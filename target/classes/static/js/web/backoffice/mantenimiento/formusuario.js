// Función para limpiar los campos del formulario y mostrar el modal de usuario para agregar un nuevo usuario
$(document).on("click", "#btnagregar", function(){
    $("#txtnombre").val("");
    $("#txtapellido").val("");
    $("#txtemail").val("");
    $("#txtemail").prop('readonly', false);
    $("#txtusuario").val("");
    $("#txtusuario").prop('readonly', false);
    $("#hddidusuario").val("0");
    $("#switchusuario").hide();
    $("#cbactivo").prop("checked", false);
    $("#divmsgpassword").show();
    $("#btnenviar").hide();
    $("#modalusuario").modal("show");
});

// Función para manejar el evento de clic en el botón de actualizar usuario
$(document).on("click", ".btnactualizar", function(){
    // Realiza una solicitud AJAX para obtener los detalles del usuario seleccionado
    $.ajax({
        type: "GET",
        url: "/mantenimiento/usuario/"+$(this).attr("data-usuid"),
        dataType: "json",
        success: function(resultado){
            // Llena el formulario con los detalles del usuario obtenido
            $("#txtnombre").val(resultado.nombres);
            $("#txtapellido").val(resultado.apellidos);
            $("#txtemail").val(resultado.email);
            $("#txtemail").prop('readonly', true);
            $("#txtusuario").val(resultado.nomusuario);
            $("#txtusuario").prop('readonly', true);
            $("#hddidusuario").val(resultado.idusuario);
            $("#switchusuario").show();
            $("#divmsgpassword").hide();
            $("#btnenviar").show();
            if(resultado.activo)
                $("#cbactivo").prop("checked", true);
            else
                $("#cbactivo").prop("checked", false);
        }
    });
    $("#modalusuario").modal("show");
});

// Imprime un mensaje en la consola del navegador
console.log("Hola, este mensaje se imprimirá en la consola del navegador");

// Función para manejar el evento de clic en el botón de guardar usuario
$(document).on("click", "#btnguardar", function(){
    // Realiza una solicitud AJAX para registrar o actualizar un usuario
    $.ajax({
        type: "POST",
        url: "/mantenimiento/usuario/registrar",
        contentType: "application/json",
        data: JSON.stringify({
            idusuario: $("#hddidusuario").val(),
            nomusuario: $("#txtusuario").val(),
            nombres: $("#txtnombre").val(),
            apellidos: $("#txtapellido").val(),
            email: $("#txtemail").val(),
            activo: $("#cbactivo").prop("checked")
        }),
        success: function(resultado){
            // Si la operación fue exitosa, actualiza la lista de usuarios
            if(resultado.respuesta){
                listarUsuarios();
            }
            // Muestra una alerta con el mensaje de la operación
            alert(resultado.mensaje);
        }
    });
    $("#modalusuario").modal("hide");
});

// Función para obtener y mostrar la lista de usuarios
function listarUsuarios(){
    $.ajax({
        type: "GET",
        url: "/mantenimiento/usuario/lista",
        dataType: "json",
        success: function(resultado){
            // Llena la tabla de usuarios con los datos obtenidos
            $("#tblusuario > tbody").html("");
            $.each(resultado, function(index, value){
                $("#tblusuario > tbody").append(`<tr>`+
                    `<td>${value.nombres}</td>`+
                    `<td>${value.apellidos}</td>`+
                    `<td>${value.nomusuario}</td>`+
                    `<td>${value.email}</td>`+
                    `<td>${value.activo}</td>`+
                    `<td><button type='button' class='btn btn-primary btnactualizar' `+
                    `data-usuid="${value.idusuario}">Actualizar`+
                    `</button></td>`+
                    `</tr>`);
            });
        }
    });
}
