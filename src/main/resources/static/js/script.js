// Variables de elementos.
const btnMenu = document.getElementById("btnMenu");
const navBar = document.querySelector(".navbar-menu");
const navBarItems = document.querySelectorAll(".navbar-menu .navbar-item");
const btnCerrar = document.getElementById("cerrar");
const btnCerrarModales = document.querySelectorAll(".cerrar-modal");
const modalOverlay = document.getElementById("modalOverlay");
const modalInfoLibro = document.querySelector(".infoLibro");
const modalInfoAutor = document.querySelector(".infoAutor");
const header = document.getElementById("header");
const modal = document.querySelector(".home"); 
const h3Modal = document.querySelector('.home-text h3');
const h3ModalAutor = document.querySelector('.modalAutor h3');
const modalInfo = document.querySelector(".home-text");
const modalImg = document.querySelector(".home-img");
const formLibro = document.querySelector(".home .form");
const formAutor = document.querySelector(".modalAutor .form");
const modalAutor = document.querySelector(".modalAutor");
const modalEditorial = document.querySelector(".modalEditorial");
const modalBorrar = document.querySelector(".borrarLibro");
const modalExito = document.getElementById("mensaje-exito");
const modalError = document.getElementById("mensaje-error");
const menuTitle = document.querySelector(".menu-title");

// Funciones de eventos
function ajustarEstiloHeader() {
    const scrollPos = window.scrollY;
    if (scrollPos > 30) {
        header.style.backgroundColor = "#ffff";
        header.style.boxShadow = "0px 0px 5px 0px grey";
    } else {
        header.style.backgroundColor = "transparent";
        header.style.boxShadow = "none";
    }
}


window.addEventListener('load', () => {
    ajustarEstiloHeader();
    ocultarMensajeExito();
    ocultarMensajeError();
 
    window.addEventListener('scroll', () => {
        ajustarEstiloHeader();
    });

   window.addEventListener('resize', () => {
    if(modalInfoAutor.classList.contains("visibleModal") || modalInfoLibro.classList.contains("visibleModal") || modalBorrar.classList.contains("visibleModal") || modal.classList.contains("visibleModal") ||    modalEditorial.classList.contains("visibleModal") ||modalAutor.classList.contains("visibleModal")){
        abrirOverlay();
    }
    else if (window.innerWidth > 600) { 
        cerrarOverlay();
        btnCerrar.style.display = "none";
    } else if (navBar.classList.contains("visible")) {
        abrirOverlay();
        btnCerrar.style.display = "block";
    }
});
    btnMenu.addEventListener("click", () => {
            navBar.classList.add("visible");    
            abrirOverlay();
            btnCerrar.style.display = "block";
        
    });
    btnCerrar.addEventListener("click",()=>{
        navBar.classList.remove("visible");
        cerrarOverlay();
    });
    btnCerrarModales.forEach(btn=>{
        btn.addEventListener("click",()=>{
            modal.classList.remove("visibleModal");
            modalAutor.classList.remove("visibleModal");
            modalEditorial.classList.remove("visibleModal");
            cerrarOverlay();
        });
    });
    navBarItems.forEach(item=>{
        item.addEventListener("click",()=>{
            navBar.classList.remove("visible");
            cerrarOverlay();
        });
    });
    modalOverlay.addEventListener("click", () => {
        modal.classList.remove("visibleModal");
        navBar.classList.remove("visible");  
        modalAutor.classList.remove("visibleModal");
        modalEditorial.classList.remove("visibleModal");
        modalBorrar.classList.remove("visibleModal");
        modalInfoLibro.classList.remove("visibleModal");
        modalInfoAutor.classList.remove("visibleModal");
        cerrarOverlay();
    });
});


///Funciones para manejar modales.
function mostrarModal(verificar) {
    if (!verificar) {
        h3Modal.textContent = "Modificar libro";
    } else {
        h3Modal.textContent = "Agregar libro";
    }
    abrirOverlay();
    modal.classList.add("visibleModal");
}
function mostrarModalAutores(verificar) {
    if (!verificar) {
        h3ModalAutor.textContent = "Modificar autor";
    } else {
        h3ModalAutor.textContent = "Agregar autor";
    }
    abrirOverlay();
    modalAutor.classList.add("visibleModal");
}
function mostrarModalModificarAutor(elemento){
    const id = elemento.getAttribute("data-id");
    const nombre = elemento.getAttribute("data-nombre");
    const nacionalidad = elemento.getAttribute("data-nacionalidad");
    const nacimiento = elemento.getAttribute("data-nacimiento");
    const imagen = elemento.getAttribute("data-imagen");
    const bio = elemento.getAttribute("data-bio");
    const inputNombre = modalAutor.querySelector('input[name="nombre"]');
    const inputNacionalidad = modalAutor.querySelector('input[name="nacionalidad"]');
    const inputNacimiento = modalAutor.querySelector('input[name="nacimiento"]');
    const inputURL = modalAutor.querySelector('input[name="imagenUrl"]');
    const inputBio = modalAutor.querySelector('input[name="bio"]');
  
    inputNombre.value = nombre;
    inputNacionalidad.value = nacionalidad;
    inputNacimiento.value = nacimiento;
    inputURL.value = imagen;
    inputBio.value = bio;
    formAutor.setAttribute("action",`/autor/modificar/${id}`);
    mostrarModalAutores(false);
}

function mostrarModalModificar(elemento) {
    const isbn = elemento.getAttribute("data-isbn");
    const titulo = elemento.getAttribute("data-titulo"); ;
    const descripcion = elemento.getAttribute("data-descripcion");
    const imagen = elemento.getAttribute("data-imagen");
    const inputISBN = document.getElementById("ib");
    const inputTitulo = modal.querySelector('input[name="titulo"]');
    const inputDescripcion = modal.querySelector('input[name="descripcion"]');
    const idAutor = elemento.getAttribute("data-autorID");
    const idEditorial = elemento.getAttribute("data-editorialID");
    const inputImagen = modal.querySelector('input[name="imagen"]');
    const selectAutor = modal.querySelector('select[name="autorID"]');
    const selectEditorial = modal.querySelector('select[name="editorialID"]');
    selectAutor.value = idAutor;
    selectEditorial.value = idEditorial;
    inputTitulo.value = titulo;
    inputDescripcion.value = descripcion;
    inputImagen.value = imagen;
    

    let isbnInput = modal.querySelector('input[name="isbn"]');
    isbnInput.disabled = true;
    let formGroup = isbnInput.parentNode;
    formGroup.style.display = "none";
    modalInfo.style.height="430px";
    modalImg.style.height="430px";
    inputISBN.disabled = true;
    formLibro.setAttribute("action",`/libro/modificar/${isbn}`);
    mostrarModal(false); 
   
}
function mostrarModalAgregar() {
    limpiarImputs();
    let isbnInput = modal.querySelector('input[name="isbn"]');
    isbnInput.disabled = false;
    let formGroup = isbnInput.parentNode;
    formGroup.style.display = "block";
    modalInfo.style.height="500px";
    modalImg.style.height="500px";
    formLibro.setAttribute("action",`/libro/registrar`);
    mostrarModal(true); 
}

function mostrarModalAutor(){
    limpiarInputsAutor();
    modalAutor.classList.add("visibleModal");
    formAutor.setAttribute("action",`/autor/registrar`);
    mostrarModalAutores(true);
} 
function mostrarModalEditorial(){
    modalEditorial.classList.add("visibleModal");
    abrirOverlay();
} 

function eliminarAutorEditorial(elemento){
    modalVisible = true;
    const id = elemento.getAttribute("data-id");
    const nombre = elemento.getAttribute("data-nombre");
    const tipo = elemento.getAttribute("data-tipo");
    const modalBorrar = document.querySelector(".borrarLibro");
    const btnBorrarLibro = document.querySelector(".borrarLibro .confirmar");
    const nombreLibroEliminar = document.querySelector(".borrarLibro .nombreLibro");
    
    abrirOverlay();
    if(tipo == "autor"){
        btnBorrarLibro.setAttribute("href",`/autor/eliminar/${id}`);
    }
    else{
        btnBorrarLibro.setAttribute("href",`/editorial/eliminar/${id}`);
    }
    nombreLibroEliminar.textContent = `${nombre}?`; 
    modalBorrar.classList.add("visibleModal");
}
function eliminarLibro(elemento){
    const isbnLibro = elemento.getAttribute("data-isbn");
    const nombreLibro = elemento.getAttribute("data-titulo");
    const modalBorrar = document.querySelector(".borrarLibro");
    const btnBorrarLibro = document.querySelector(".borrarLibro .confirmar");
    const nombreLibroEliminar = document.querySelector(".borrarLibro .nombreLibro");
    abrirOverlay();
    btnBorrarLibro.setAttribute("href",`/libro/eliminar/${isbnLibro}`)
    nombreLibroEliminar.textContent = `${nombreLibro}?`; 
    modalBorrar.classList.add("visibleModal");
}
function cerrarConfirmacion(){
    cerrarOverlay();
    modalBorrar.classList.remove("visibleModal");

}
function cerrarOverlay() {
    modalOverlay.style.display = "none";
    document.body.style.overflow = 'auto';
}

function abrirOverlay(){
    modalOverlay.style.display = "block";
    document.body.style.overflow = 'hidden';
}

function mostrarInfoAutor(elemento){
    const id = elemento.getAttribute("data-id");
    const nombre = elemento.getAttribute("data-nombre");
    const nacionalidad = elemento.getAttribute("data-nacionalidad");
    const fechaNacimiento = elemento.getAttribute("data-nacimiento");
    const bio = elemento.getAttribute("data-bio");
    const urlImagen = elemento.getAttribute("data-imagen");
    const paises = [
        { nombre: ['argentina', 'argentino'], bandera: '🇦🇷' },
        { nombre: ['brasilero', 'brasilera', 'brasileño', 'brasileña'], bandera: '🇧🇷' },
        { nombre: ['español', 'española'], bandera: '🇪🇸' },
        { nombre: ['británica', 'británico', 'britanica', 'britanico'], bandera: '🇬🇧' },
        { nombre: ['estadounidense','americano','americana'], bandera: '🇺🇸' },
        { nombre: ['japonés', 'japones', 'japonesa'], bandera: '🇯🇵' },
        { nombre: ['frances', 'francés', 'francesa'], bandera: '🇫🇷' },
        { nombre: ['aleman', 'alemán', 'alemana'], bandera: '🇩🇪' },
        { nombre: ['chino', 'china','chinese'], bandera: '🇨🇳' },
        { nombre: ['mexicano', 'mexicana'], bandera: '🇲🇽' },
        { nombre: ['colombiano', 'colombiana'], bandera: '🇨🇴' },
        { nombre: ['peruano', 'peruana'], bandera: '🇵🇪' },
        { nombre: ['uruguayo', 'uruguaya'], bandera: '🇺🇾' },
        { nombre: ['chileno', 'chilena'], bandera: '🇨🇱' },
        { nombre: ['venezolano', 'venezolana'], bandera: '🇻🇪' },
        { nombre: ['checo', 'checa'], bandera: '🇨🇿' },  
        { nombre: ['ruso', 'rusa'], bandera: '🇷🇺' },  
        { nombre: ['polaco', 'polaca'], bandera: '🇵🇱' }, 
        { nombre: ['italiano', 'italiana'], bandera: '🇮🇹' },
        { nombre: ['griego', 'griega'], bandera: '🇬🇷' }, 
        { nombre: ['irlandés', 'irlandes', 'irlandesa'], bandera: '🇮🇪' }, 
        { nombre: ['inglés', 'ingles', 'inglesa', 'inglaterra'], bandera: '&#127988' },
        { nombre: ['noruego', 'noruega'], bandera: '🇳🇴' }, 
        { nombre: ['sueco', 'sueca'], bandera: '🇸🇪' }, 
        { nombre: ['danés', 'danes', 'danesa'], bandera: '🇩🇰' },
    ];
    const banderas = {};

    paises.forEach(pais => {
        pais.nombre.forEach(nombre => {
            banderas[nombre] = pais.bandera;
        });
    });
    
    cantidad = contarLibrosPorAutor(id);

    let genero = nacionalidad.charAt(nacionalidad.length - 1);
    let mensaje = ""; 
    
    const autorOAutora = (genero == 'a') ? "autora" : "autor";
    const estaOeste = (genero == 'a') ? "esta" : "este";
    
    if (cantidad <= 0) {
        mensaje = `No hay libros de ${estaOeste} ${autorOAutora}.`;
    } else if (cantidad == 1) {
        mensaje = `Hay ${cantidad} libro de ${estaOeste} ${autorOAutora}.`;
    } else {
        mensaje = `Hay ${cantidad} libros de ${estaOeste} ${autorOAutora}.`;
    }
    

    const bandera = banderas[nacionalidad.toLowerCase()] || ""; 
    modalInfoAutor.innerHTML = `
    <a class="cerrar-modal"><i class="bi bi-x"></i></a>
    <div class="detalle-autor">
        <div class="imagenAutor" >
        <img src="${urlImagen}" alt="imagen-autor">
        </div>
        <div class="info-autor">
        <h3>${nombre}</h3>
        <p>${nacionalidad} ${bandera}</p>
        <p>Nacimiento: ${fechaNacimiento}</p>
        <p>Biografía: <a href="${bio}" target="_BLANK">ir a Wikipedia</a></p>
        <p class="mensajeCantidad">${mensaje}</p>
        </div>
    </div>
`;

const btnCerrar = modalInfoAutor.querySelector(".cerrar-modal");
btnCerrar.addEventListener("click", () => {
    cerrarOverlay();
    modalInfoAutor.classList.remove("visibleModal");
});

abrirOverlay();
modalInfoAutor.classList.add("visibleModal");
};

function mostrarInfoLibro(elemento) {
    const isbn = elemento.getAttribute("data-isbn");
    const titulo = elemento.getAttribute("data-titulo");
    const autor = elemento.getAttribute("data-autor");
    const editorial = elemento.getAttribute("data-editorial");
    const descripcion = elemento.getAttribute("data-descripcion");
    const urlImagen = elemento.getAttribute("data-imagen");
    const fecha = elemento.getAttribute("data-fecha");
    if (urlImagen === null) {
        modalInfoLibro.innerHTML = `
        <a class="cerrar-modal"><i class="bi bi-x"></i></a>
        <div class="detalle-libro no-imagen">
            <div class="info-libro">
            <h3>${titulo}</h3>
            <p>ISBN: ${isbn}</p>
            <p>Autor: ${autor}</p>
            <p>Editorial: ${editorial}</p>
            <p>Fecha de alta: ${fecha}</p>
            <p>Descripción:<br>${descripcion}</p>
            </div>
        </div>
        `;
        modalInfoLibro.classList.add("no-imagen");
    } else {
        modalInfoLibro.innerHTML = `
        <a class="cerrar-modal"><i class="bi bi-x"></i></a>
        <div class="detalle-libro">
            <div class="imagenLibro">
            <img src="${urlImagen}" alt="Portada del libro">
            </div>
            <div class="info-libro">
            <h3>${titulo}</h3>
            <p>Autor: ${autor}</p>
            <p>Editorial: ${editorial}</p>
            <p>Fecha de alta: ${fecha}</p>
            <p>Descripción: ${descripcion}</p>
            </div>
        </div>
        `;
        modalInfoLibro.classList.remove("no-imagen");
    }

    const btnCerrar = modalInfoLibro.querySelector(".cerrar-modal");
    btnCerrar.addEventListener("click", () => {
        cerrarOverlay();
        modalInfoLibro.classList.remove("visibleModal");
    });

    abrirOverlay();
    modalInfoLibro.classList.add("visibleModal");

}

///Funciones extras.
function ocultarMensajeExito() {
    setTimeout(()=>{
        if(modalExito !== null){
            modalExito.style.display = "none";
        }
    },2000);   
}
function ocultarMensajeError(){
    setTimeout(()=>{
        if(modalError !== null){
            modalError.style.display = "none";
        }
    },2000)
}

function ordenarLibrosAlfabeticamente() {
    var listaLibros = document.querySelectorAll("#librosTable tbody tr");
    var librosArray = Array.from(listaLibros);

    librosArray.sort(function (a, b) {
        var tituloA = a.querySelector("td:nth-child(2)").textContent.trim().toLowerCase();
        var tituloB = b.querySelector("td:nth-child(2)").textContent.trim().toLowerCase();
        if (tituloA < tituloB) return -1;
        if (tituloA > tituloB) return 1;
        return 0;
    });
    var contenedorLibros = document.querySelector("#librosTable tbody");
    contenedorLibros.innerHTML = "";

    librosArray.forEach(function (libro) {
        contenedorLibros.appendChild(libro);
    });
};
function contarLibrosPorAutor(idAutor) {
    let filasLibros = document.querySelectorAll('#librosTable tbody tr');
    let idAutores = [];
    
    filasLibros.forEach(elemento => {
        idAutores.push(elemento.getAttribute("data-autorID"));
    })
    
    let cantidad = 0;

    idAutores.forEach(id=>{
        if(idAutor === id){
            cantidad++;
        }
    });

    return cantidad;
}

//Funciones limpiar elementos.
function limpiarImputs(){
    const inputTitulo = modal.querySelector('input[name="titulo"]');
    const inputDescripcion = modal.querySelector('input[name="descripcion"]');
    const inputImagen = modal.querySelector('input[name="imagen"]');
    const selectAutor = modal.querySelector('select[name="autorID"]');
    const selectEditorial = modal.querySelector('select[name="editorialID"]');

    inputTitulo.value = "";
    inputImagen.value = "";
    inputDescripcion.value = "";
    selectAutor.value = "";
    selectEditorial.value="";
}

function limpiarInputsAutor(){
    const inputNombre = modalAutor.querySelector('input[name="nombre"]');
    const inputNacionalidad = modalAutor.querySelector('input[name="nacionalidad"]');
    const inputURL = modalAutor.querySelector('input[name="imagenUrl"]');
    const inputBio = modalAutor.querySelector('input[name="bio"]');
    const inputNacimiento = modalAutor.querySelector('input[name="nacimiento"]');    
    inputNombre.value = "";
    inputNacionalidad.value = "";
    inputURL.value = "";
    inputBio.value = "";
    inputNacimiento.value = "";
}


