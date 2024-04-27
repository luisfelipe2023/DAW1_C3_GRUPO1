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
$(document).on("click", ".btnactualizar", function(){
    $.ajax({
        type: "GET",
        url: "/mantenimiento/usuario/"+$(this).attr("data-usuid"),
        dataType: "json",
        success: function(resultado){
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
    })
    $("#modalusuario").modal("show");
})
console.log("Hola, este mensaje se imprimirá en la consola del navegador");

$(document).on("click", "#btnguardar", function(){
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
            if(resultado.respuesta){
                listarUsuarios()
            }
            alert(resultado.mensaje);
        }
    });
    $("#modalusuario").modal("hide");
});

function listarUsuarios(){
    $.ajax({
        type: "GET",
        url: "/mantenimiento/usuario/lista",
        dataType: "json",
        success: function(resultado){
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