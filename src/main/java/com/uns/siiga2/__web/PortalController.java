package com.uns.siiga2.__web; 

import java.util.Optional; // Necesario para manejar usuarios que podrían no existir
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Este es el Cerebro de la aplicación web.
 * La anotación @Controller le dice a Spring que esta clase
 * manejará las peticiones del navegador.
 */
@Controller
public class PortalController {

    // 1. Inyectamos nuestro Repositorio para poder usar la base de datos.
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * FUNCIÓN 1: Muestra la página de inicio (las 3 opciones).
     * Se activa cuando alguien visita: http://localhost:8085/
     */
    @GetMapping("/")
    public String mostrarInicio() {
        // "inicio" es el nombre del archivo HTML que buscará.
        // (Lo crearemos en el siguiente paso).
        return "inicio"; 
    }

    /**
     * FUNCIÓN 2: Muestra el formulario de login para un rol específico.
     * Se activa con URLs como: /login/ADMINISTRADOR, /login/DOCENTE, etc.
     */
    @GetMapping("/login/{rol}")
    public String mostrarLogin(@PathVariable("rol") String rol, Model model) {
        // "model" es el objeto que usamos para pasar datos de Java al HTML.
        model.addAttribute("tipoAcceso", rol); // Le pasamos el rol al HTML
        return "login"; // Busca y muestra el archivo "login.html"
    }

    /**
     * FUNCIÓN 3: Procesa los datos del formulario de login.
     * Se activa cuando el formulario de login.html envía los datos.
     */
    @PostMapping("/login")
    public String procesarLogin(
        @RequestParam String username, 
        @RequestParam String password, 
        @RequestParam(required = false) String tipoAcceso, // Añadido
        Model model,
        HttpSession session,
        HttpServletRequest request) { // Añadido para obtener método de solicitud
    
    // Solo validar con JavaScript si es una solicitud AJAX/normal
    // Pero mantendremos la validación del servidor
    
    Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

    if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
        
        Usuario usuario = usuarioOpt.get();
        
        if (tipoAcceso != null && !usuario.getRol().name().equals(tipoAcceso)) {
            model.addAttribute("error", "Acceso denegado. Este usuario no tiene permisos de " + tipoAcceso);
            model.addAttribute("tipoAcceso", tipoAcceso); // Mantener en mayúsculas
            return "login";
        }
        
        // Guardamos el objeto "usuario" completo en la memoria de la sesión.
        session.setAttribute("usuarioLogueado", usuario);
        
        switch (usuario.getRol()) {
            case ADMINISTRADOR:
                return "redirect:/horario-admin";
            case DOCENTE:
                return "redirect:/horario-profesor";
            case ALUMNO:
                return "redirect:/horario-alumno";
        }
    }

    // Si llega aquí, las credenciales son incorrectas
    // Verificamos si es una solicitud AJAX (opcional, pero buena práctica)
    String requestedWithHeader = request.getHeader("X-Requested-With");
    boolean isAjax = "XMLHttpRequest".equals(requestedWithHeader);
    
    if (isAjax) {
        // Para solicitudes AJAX, puedes devolver JSON
        // Pero como estamos usando formulario normal, no aplica
    }
    
    // En lugar de cambiar el tipoAcceso a "Error", mantenemos el original
    // y añadimos un atributo para mostrar el error
    model.addAttribute("error", "Usuario o contraseña incorrectos");
    model.addAttribute("tipoAcceso", tipoAcceso != null ? tipoAcceso : "Usuario"); // Mantiene el rol
    
    return "login"; // Vuelve a la misma página
    }
    
    @GetMapping("/horario-admin")
    public String mostrarHorarioAdmin(Model model) { // <-- Añadimos "Model model"
        // Buscamos todos los cursos en la BD
        List<Curso> todosLosCursos = cursoRepository.findAll();
        
        // Se los pasamos al HTML
        model.addAttribute("listaCursos", todosLosCursos);
        
        return "horario-admin"; // Muestra "horario-admin.html"
    }
    
    @GetMapping("/cursos/nuevo")
    public String mostrarFormularioDeCurso() {
        return "crear-curso"; // Muestra el HTML "crear-curso.html"
    }
    
    @PostMapping("/cursos/guardar")
    public String guardarNuevoCurso(
            @RequestParam String nombreCurso,
            @RequestParam String horario,
            @RequestParam String docenteUsername) {
        
        // 1. Creamos un nuevo objeto Curso (el "molde")
        Curso nuevoCurso = new Curso();
        
        // 2. Llenamos el molde con los datos del formulario
        nuevoCurso.setNombreCurso(nombreCurso);
        nuevoCurso.setHorario(horario);
        nuevoCurso.setDocenteUsername(docenteUsername); // Asignamos al docente por su username
        
        // 3. Usamos el "puente" para guardar el objeto en Supabase
        cursoRepository.save(nuevoCurso);
        
        // 4. Redirigimos al admin de vuelta a su panel
        return "redirect:/horario-admin"; 
    }
    
    @GetMapping("/horario-profesor")
    public String mostrarHorarioDocente(HttpSession session, Model model) { // <-- Añadimos Sesión y Model
        
        // 1. Sacamos al usuario de la sesión
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        
        // 2. Verificamos que alguien esté logueado
        if (usuario == null) {
            return "redirect:/"; // Si no hay nadie, lo botamos al inicio
        }
        
        // 3. Buscamos en el Repositorio de Cursos
        List<Curso> misCursos = cursoRepository.findByDocenteUsername(usuario.getUsername());
        
        // 4. Pasamos los datos al HTML
        model.addAttribute("nombreDocente", usuario.getUsername());
        model.addAttribute("listaCursos", misCursos);
        
        return "horario-profesor";
    }

    @GetMapping("/horario-alumno")
    public String mostrarHorarioAlumno(HttpSession session, Model model) { // <-- Nombre de función actualizado
        
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/";
        }
        
        // 1. Buscamos todas las matrículas de ESTE alumno
        // (Usamos el método findByAlumnoId que creamos en el Paso 14)
        List<Matricula> misMatriculas = matriculaRepository.findByAlumnoId(usuario.getId());
        
        // 2. Pasamos los datos al HTML
        model.addAttribute("nombreAlumno", usuario.getUsername());
        model.addAttribute("listaMatriculas", misMatriculas);
        
        return "horario-alumno"; 
    }

    /**
     * FUNCIÓN 5: Muestra la página para crear un nuevo usuario.
     * Se activa con la URL: /usuarios/nuevo
     */
    @GetMapping("/usuarios/nuevo")
    public String mostrarFormularioDeRegistro(Model model) {
        // Le pasamos la lista de todos los roles (ADMINISTRADOR, DOCENTE, TRABAJADOR)
        // para que el <select> del HTML pueda mostrarlos.
        model.addAttribute("listaDeRoles", Rol.values());
        return "registro-usuario"; // Muestra el HTML que creamos
    }

    /**
     * FUNCIÓN 6: Recibe los datos del formulario y guarda el nuevo usuario.
     * Se activa cuando el formulario de "registro-usuario.html" envía los datos.
     */
    @PostMapping("/usuarios/guardar")
    public String guardarNuevoUsuario(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Rol rol) {
        
        // 1. Creamos un nuevo objeto Usuario (el "molde")
        Usuario nuevoUsuario = new Usuario();
        
        // 2. Llenamos el molde con los datos del formulario
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password); // (Recuerda que en un proyecto real, esto debe encriptarse)
        nuevoUsuario.setRol(rol);
        
        // 3. Usamos el "puente" para guardar el objeto en Supabase
        usuarioRepository.save(nuevoUsuario);
        
        // 4. Redirigimos al usuario de vuelta a una página (ej. la de admin)
        return "redirect:/horario-admin"; 
    }

    @GetMapping("/admin/curso/{id}")
    public String adminVerNotas(@PathVariable("id") Long cursoId, Model model) {
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        List<Matricula> lista = matriculaRepository.findByCursoId(cursoId);
        
        model.addAttribute("nombreCurso", curso.getNombreCurso());
        model.addAttribute("listaMatriculas", lista);
        
        return "admin-editar-notas";
    }
    
    @GetMapping("/cursos/eliminar/{id}")
    public String eliminarCurso(@PathVariable("id") Long id) {
        
        // 1. ¡Aquí está la magia! Spring Data JPA nos da este método gratis.
        //    Simplemente le decimos al "puente" que borre el objeto
        //    que tenga este ID.
        cursoRepository.deleteById(id);
        
        // 2. Redirigimos al admin de vuelta a su panel.
        //    La página se recargará y el curso habrá desaparecido.
        return "redirect:/horario-admin"; 
    }
    @Autowired
    private MatriculaRepository matriculaRepository;
    /**
     * FUNCIÓN 7: Mostrar formulario de Matrícula
     * Muestra la página para inscribir a un alumno en un curso.
     */
    @GetMapping("/matriculas/nueva")
    public String mostrarFormularioMatricula(Model model) {
        // Buscamos solo a los ALUMNOS para la lista desplegable
        List<Usuario> listaAlumnos = usuarioRepository.findByRol(Rol.ALUMNO);
        
        // Buscamos todos los CURSOS para la lista desplegable
        List<Curso> listaCursos = cursoRepository.findAll();
        
        model.addAttribute("alumnos", listaAlumnos);
        model.addAttribute("cursos", listaCursos);
        
        return "matricular-alumno";
    }

    /**
     * FUNCIÓN 8: Guardar la Matrícula
     * Recibe el ID del alumno y el ID del curso y crea la relación.
     */
    @PostMapping("/matriculas/guardar")
    public String guardarMatricula(
            @RequestParam Long alumnoId,
            @RequestParam Long cursoId) {
        
        // 1. Buscamos los objetos completos en la BD
        // (.orElse(null) es para evitar errores si no lo encuentra)
        Usuario alumno = usuarioRepository.findById(alumnoId).orElse(null);
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        
        boolean exiteLaMatricula = matriculaRepository.existsByCursoIdAndAlumnoId(cursoId, alumnoId);

        if(!exiteLaMatricula){
            if (alumno != null && curso != null) {
            // 2. Creamos la nueva matrícula
            Matricula nuevaMatricula = new Matricula();
            nuevaMatricula.setAlumno(alumno);
            nuevaMatricula.setCurso(curso);
            
            // Inicializamos las notas en 0 para evitar errores matemáticos luego
            nuevaMatricula.setNota1(0.0);
            nuevaMatricula.setNota2(0.0);
            nuevaMatricula.setPromedio(0.0);
            
            // 3. Guardamos en la base de datos
            matriculaRepository.save(nuevaMatricula);
            }
        }
        return "redirect:/horario-admin";
    }
    
    /**
     * FUNCIÓN 9: Ver alumnos de un curso (Para el Profesor)
     */
    @GetMapping("/profesor/curso/{id}")
    public String verAlumnosDelCurso(@PathVariable("id") Long cursoId, Model model) {
        
        // 1. Buscamos el curso para poner el nombre en el título
        Curso curso = cursoRepository.findById(cursoId).orElse(null);
        
        // 2. Buscamos todas las matrículas de ese curso
        // (Usamos el método que creamos en el Paso 14)
        List<Matricula> lista = matriculaRepository.findByCursoId(cursoId);
        
        model.addAttribute("nombreCurso", curso.getNombreCurso());
        model.addAttribute("listaMatriculas", lista);
        
        return "calificar-curso";
    }

    /**
     * FUNCIÓN 10: Guardar notas y calcular promedio
     */
    @PostMapping("/profesor/guardar-nota")
    public String guardarNota(
            @RequestParam Long matriculaId,
            @RequestParam Double nota1,
            @RequestParam Double nota2) {
        
        // 1. Buscamos la matrícula en la BD
        Matricula matricula = matriculaRepository.findById(matriculaId).orElse(null);
        
        if (matricula != null) {
            // 2. Actualizamos las notas
            matricula.setNota1(nota1);
            matricula.setNota2(nota2);
            
            // 3. Calculamos el promedio simple
            double promedio = (nota1 + nota2) / 2.0;
            matricula.setPromedio(promedio);
            
            // 4. Guardamos
            matriculaRepository.save(matricula);
            
            // 5. Redirigimos de vuelta a la lista del mismo curso
            //getId() : ubicarme con el ID de la matrícula
            //getCurso() : me ubica en la parte de curso_id(BD) y me devuelve ese número(curso)
            return "redirect:/profesor/curso/" + matricula.getCurso().getId();
        }
        
        return "redirect:/horario-profesor";
    }

    /**
     * FUNCIÓN 12: Guardar nota SEGURA (Con código)
     */
    @PostMapping("/admin/guardar-nota-segura")
    public String adminGuardarNotaSegura(
            @RequestParam Long matriculaId,
            @RequestParam Double nota1,
            @RequestParam Double nota2,
            @RequestParam String codigoSecreto,
            Model model) {
        
        // 1. VALIDAMOS EL CÓDIGO SECRETO
        // (En la vida real, esto estaría en application.properties)
        if (!codigoSecreto.equals("SIIGA2025")) {
            
            // Si falla, tenemos que volver a cargar la página con un error.
            // Como es un redirect complicado, por simplicidad devolvemos error genérico:
            return "redirect:/horario-admin?error=CodigoIncorrecto";
        }

        // 2. Si el código es correcto, procedemos igual que el profesor
        Matricula matricula = matriculaRepository.findById(matriculaId).orElse(null);
        
        if (matricula != null) {
            matricula.setNota1(nota1);
            matricula.setNota2(nota2);
            matricula.setPromedio((nota1 + nota2) / 2.0);
            matriculaRepository.save(matricula);
            
            // Volvemos a la misma lista
            return "redirect:/admin/curso/" + matricula.getCurso().getId();
        }
        
        return "redirect:/horario-admin";
    }
}