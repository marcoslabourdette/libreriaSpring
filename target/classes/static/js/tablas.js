//Cambio de idioma los elementos del Datatable.
let language_es = {
    "decimal": "",
    "emptyTable": "No hay datos disponibles en la tabla",
    "info": "  Mostrando _START_ a _END_ de _TOTAL_ registros",
    "infoEmpty": "Mostrando 0 to 0 of 0 registros",
    "infoFiltered": " ",
    "infoPostFix": "",
    "thousands": ",",
    "lengthMenu": "Mostrar _MENU_ entradas",
    "loadingRecords": "Cargando...",
    "processing": "Procesando...",
    "search": "Buscar:",
    "zeroRecords": "No se encontraron registros coincidentes",
    "paginate": {
        "first": "Primero",
        "last": "Último",
        "next": "Siguiente",
        "previous": "Anterior"
    },
    "aria": {
        "sortAscending": ": activar para ordenar la columna ascendente",
        "sortDescending": ": activar para ordenar la columna descendente"
    }
};
$(document).ready(function () {
    let tablaLibros = $("#tablaLibros").DataTable({
        "columnDefs": [
            { "orderable": false, "targets": [3, 4] }
        ],
        "pageLength": 4,
        "language": language_es,
        "lengthChange": false,
        "columns": [
            { "width": "20%" }, 
            { "width": "30%" }, 
            { "width": "25%" }, 
            { "width": "10%" }, 
            { "width": "15%" }  
        ]
    });

    $(window).resize(function() {
        if ($(window).width() < 1190) {
            tablaLibros.column(0).visible(false);
        } else {
            tablaLibros.column(0).visible(true);
        }
        tablaLibros.columns.adjust().draw();
    }).resize();
});

$(document).ready(function () {
    $('#tablaEditoriales').DataTable({
        "columnDefs": [
            { "orderable": false, "targets": 2 }
        ],
        "pageLength": 4,
        "language": language_es,
        "lengthChange": false,
        "columns": [
            { "width": "40%" }, 
            { "width": "40%" }, 
            { "width": "20%" } 
        ]
    });
});

$(document).ready(function () {
    let tablaAutores = $('#tablaAutores').DataTable({
        "columnDefs": [
            { "orderable": false, "targets": [3, 4] }
        ],
        "pageLength": 4,
        "language": language_es,
        "lengthChange": false,
        "searching": true,
        "columns": [
            { "width": "30%" }, 
            { "width": "25%" }, 
            { "width": "20%" }, 
            { "width": "5%" },  
            { "width": "20%" }  
        ]
    });

    $(window).resize(function() {
        if ($(window).width() < 1190) {
            tablaAutores.column(2).visible(false);
            tablaAutores.column(0).visible(true); 
        } else {
            tablaAutores.column(2).visible(true);
        }
        if ($(window).width() < 395) {
            tablaAutores.column(1).visible(false);
        } else {
            tablaAutores.column(1).visible(true);
        }
        tablaAutores.columns.adjust().draw();
    }).resize();
});

