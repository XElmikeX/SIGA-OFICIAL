// Esperar a que el documento esté listo
        document.addEventListener('DOMContentLoaded', function() {
            console.log('JavaScript cargado correctamente!');
            
            // Seleccionar todos los enlaces con data-desplazamiento
            const enlaces = document.querySelectorAll('a[data-desplazamiento]');
            
            // Agregar evento a cada enlace
            enlaces.forEach(function(enlace) {
                enlace.addEventListener('click', function(evento) {
                    evento.preventDefault(); // Evitar el comportamiento por defecto
                    
                    // Obtener el destino y el desplazamiento
                    const href = this.getAttribute('href');
                    const destinoId = href.substring(1); // Quitar el #
                    const desplazamiento = parseInt(this.getAttribute('data-desplazamiento')) || 0;
                    
                    // Buscar el elemento destino
                    const destino = document.getElementById(destinoId);
                    
                    if (destino) {
                        // Calcular la posición final
                        const posicionDestino = destino.offsetTop;
                        const posicionFinal = posicionDestino - desplazamiento;
                        
                        // Hacer scroll suave
                        window.scrollTo({
                            top: posicionFinal,
                            behavior: 'smooth'
                        });
                        
                        console.log('Navegando a: ' + destinoId + ' con desplazamiento: ' + desplazamiento + 'px');
                    } else {
                        console.warn('No se encontró el elemento con id: ' + destinoId);
                    }
                });
            });
        });