const formLogin = document.querySelector(".login .form-login");
const inputName = document.querySelector(".form-login input[type='text']");
const inputPass = document.querySelector(".form-login input[type='password']");
const alertaExitoLogin = document.querySelector(".form .alerta-exito");
const alertaErrorLogin = document.querySelector(".form .alerta-error");
const alertaServidor = document.querySelector(".alerta-error-servidor");

// Permite como debe ser el input introducido
const userNameRegex = /^[a-zA-Z0-9\_\-]{4,16}$/;
const passwordRegex = /^.{4,16}$/;

/* VERIFICAR SI TODOS LOS INPUT SE LLENARON CORRECTAMENTE */
const estadoValidacionCampos = {
    username: false, 
    password: false,  
};

// Para asegurarnos que primero cargue la pagina
document.addEventListener("DOMContentLoaded", () => {

    if (alertaServidor && alertaServidor.textContent.trim() !== '') {
        // Solo mostrar si el contenido NO es el placeholder por defecto
        mostrarErrorServidor();
    }

    formLogin.addEventListener("submit", (e) => {
        enviarFormulario(e);
    });

    inputName.addEventListener("input", () => {
        validarCampo(userNameRegex, inputName, "El usuario tiene que ser de 4 a 16 digitos y solo puede contener,letras y guión bajo");
    });
    
    inputPass.addEventListener("input", () => {
        validarCampo(passwordRegex, inputPass, "La contraseña debe tener de 4 a 16 digitos");
    });
    
    // Aplicar color según el rol
    aplicarColorSegunRol();
});

function validarCampo(regularExpresion, campo, mensaje){
    const esValido = regularExpresion.test(campo.value);
    if (esValido){
        eliminarAlerta(campo.parentElement.parentElement);
        estadoValidacionCampos[campo.name] = true;
        campo.parentElement.classList.remove("error");
        return;
    }
    mostrarAlerta(campo.parentElement.parentElement, mensaje);
    estadoValidacionCampos[campo.name] = false;
    campo.parentElement.classList.add("error");
};

function mostrarAlerta(referencia, mensaje){
    eliminarAlerta(referencia);
    const alertaDiv = document.createElement("div");
    alertaDiv.classList.add("alerta");
    alertaDiv.textContent = mensaje;
    referencia.appendChild(alertaDiv);
};

function eliminarAlerta(referencia){
    const alerta = referencia.querySelector(".alerta");
    if (alerta){
        alerta.remove();
    }
};

function mostrarErrorServidor() {
    if (alertaServidor) {
        alertaExitoLogin.classList.remove('alertaExito');
        alertaServidor.style.display = 'block';
        
        // Ocultar después de 4 segundos
        setTimeout(() => {
            alertaServidor.style.display = 'none';
        }, 4000);
    }
}

function enviarFormulario(e){
    e.preventDefault();
    
    // Validar todos los campos antes de enviar
    validarCampo(userNameRegex, inputName, "El usuario tiene que ser de 4 a 16 digitos y solo puede contener,letras y guión bajo");
    validarCampo(passwordRegex, inputPass, "La contraseña debe tener de 4 a 16 digitos");
    
    if (estadoValidacionCampos.username && estadoValidacionCampos.password) {
        // ÉXITO: mostrar alerta verde y enviar
        estadoValidacionCampos.username = false;
        estadoValidacionCampos.password = false;

        if (alertaErrorLogin){
            alertaErrorLogin.style.display = 'none';
        };
        if (alertaServidor){
            alertaServidor.style.display = 'none';
        };
        
        if (alertaExitoLogin) {
            alertaExitoLogin.textContent = 'Enviado';
            alertaExitoLogin.classList.add('alertaExito');
        }

        setTimeout(() => {
            alertaExitoLogin.classList.remove('alertaExito');
            formLogin.submit();
        }, 500);
    } else {
        // ERROR: mostrar alerta roja
        if (alertaExitoLogin) alertaExitoLogin.style.display = 'none';
        
        if (alertaErrorLogin) {
            alertaErrorLogin.textContent = 'Por favor, corrige los errores en los campos';
            alertaErrorLogin.classList.add('alertaError');
        }

        setTimeout(() => {
            alertaErrorLogin.classList.remove('alertaError');
        }, 3000);
    }
};

function aplicarColorSegunRol() {
    const tipoAccesoInput = document.querySelector('input[name="tipoAcceso"]');
    const infoDiv = document.querySelector('.information');
    
    if (tipoAccesoInput && tipoAccesoInput.value) {
        const rol = tipoAccesoInput.value;
        
        if (rol.includes('ADMIN')) {
            infoDiv.style.background = 'radial-gradient(ellipse at center right, #000000ff, #c21d1dff)';
        } else if (rol.includes('DOCEN')) {
            infoDiv.style.background = 'radial-gradient(ellipse at center right, #000000ff, #32c51eff)';
        } else if (rol.includes('ALUMN')) {
            infoDiv.style.background = 'radial-gradient(ellipse at center right, #000000ff, #45d1f4ff)';
        }
    }
}
