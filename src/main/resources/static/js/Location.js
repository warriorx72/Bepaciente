$(document).ready(function()
{	

}); 
function CerrarCalendario()
{
	$('#calendar').remove();
	$('#botonCerrarCalendario').hide();
	getLocation();
}

function getLocation() 
{
	var estadoIngresado = $('#estadoIngresado').val();
	var especialidadIngresada = $('#Especialidad').val();
	var estadoCorrecto = true;
	var especialidadCorrecta = true;
	
	if(estadoIngresado == 'Estado')
	{
		$('#WarningEstado').show();
		$('#TitleEstado').hide();
		estadoCorrecto = false;
	}
	
	if(especialidadIngresada == 'None')
	{
		$('#WarningEspecialidad').show();
		$('#TitleEspecialidad').hide();
		especialidadCorrecta = false;
	}
	
	
	if(estadoCorrecto == true && especialidadCorrecta == true)
	{
			if(estadoIngresado != "Estado")
			{
				EnviarEstado(estadoIngresado);
			}
			else
			{
				if (navigator.geolocation) 
				{
					navigator.geolocation.getCurrentPosition(showPosition);
				} 
				else 
				{
				    x.innerHTML = "Geolocation is not supported by this browser.";
				}
			}
			
			$('#WarningEstado').hide();
			$('#TitleEstado').show();
			$('#WarningEspecialidad').hide();
			$('#TitleEspecialidad').show();
	}
}

function showPosition(position) 
{
	var lat = position.coords.latitude;
	var lon = position.coords.longitude;
	MandarCoordenadas(lat, lon);
}


function MandarCoordenadas(lat, lon)
{
	//Variables
	var Estado;
	
	
	//Solicitud Ajax
    $.ajax({
        type: "GET",
        url: "https://geocode.xyz/" + lat + "," + lon +"?geoit=json",
        success: (data) => {
        	if(data.region.split(', ')[1] == undefined)
        	{
        		Estado = data.region;
        	}
        	else
        	{
        		Estado = data.region.split(', ')[1];
        	}
        	
        	switch(Estado)
        	{
        		case "México":
        			Estado = "Estado de México";
        			break;
        		case "Mexico City":
        			Estado = "Ciudad de México"
        			break;
        	}
        	
        	//Accion final del success de Ajax
        	EnviarEstado(Estado);

        },
        error: (e) => {
        	console.log("Error");
        }
      });
}


function EnviarEstado(estado)
{
	var especialidad = $('#Especialidad').val();
	console.log(especialidad, estado);
	//Solicitud Ajax
    $.ajax({
        type: "GET",
        url: "/buscar_doctor",
        data: {estado: estado, especialidad: especialidad},
        success: (data) => {
        	console.log(data);
        	if(data[0] != null)
        	{
        		$('.Quitar').remove();
            	$('#mensajeAjax').text(" ");
            	data.forEach(PintarMedicos);
            	VueltasAppend = 0;
        	}
        	else
        	{
        		$('#mensajeAjax').text("No se encontraron medicos");
        		$('.Quitar').remove();
        	} 
        },
        error: (e) => {
        	console.log("Error");
        }
      });
}

function ObtenerInfo(id)
{
    $.ajax({
        type: "GET",
        url: "/buscar_doctor_info",
        data: {id: id},
        success: (data) => {
        	console.log(data);
        	var obj = JSON.parse(data);
        	console.log(obj);
        	$('#NombreDoctor').remove();
        	$('#DoctorEspecialidad').remove();
        	$('#DoctorTelefono').remove();
        	$('#DoctorPrimerConsulta').remove();
        	$('#DoctorControlConsulta').remove();
        	$('#DoctorDomicilioConsulta').remove();
        	
        	if(obj['externo'] == null || obj['externo'] == 0 || obj['interno'] == null || obj['interno'] == 0)
        	{
            	direccion = obj['calle'] + " S/N. " + obj['colonia'] + ", " + obj['municipio'] + ". " + obj['estado'] + ", CP: " + obj['cp'];
        	}
        	else
        	{
            	direccion = obj['calle'] + " #" + obj['externo'] + ": INT: " + obj['interno'] + " " + obj['colonia'] + ", " + obj['municipio'] + ". " + obj['estado'] + ", CP: " + obj['cp'];
        	}
        	
        	$('#ContNombreDoctor').append("<p id='NombreDoctor'><strong>Médico: </strong>" + obj['nombre'] + " " + obj['pat'] + " " + obj['mat'] + "</p>");
        	$('#ContainerEspecialidad').append("<p id='DoctorEspecialidad'>" + obj['espe'] + "</p>");
        	$('#ContainerTelefono').append("<p id='DoctorTelefono'>" + obj['tel'] + "</p>");
        	$('#ContainerPrimerConsulta').append("<p id='DoctorPrimerConsulta'>$" + obj['pricon'] + "</p>");
        	$('#ContainerControlConsulta').append("<p id='DoctorControlConsulta'>$" + obj['concon'] +"</p>");
        	$('#ContainerDomicilioConsulta').append("<p id='DoctorDomicilioConsulta'>$" + obj['domcon'] + "</p>");
        	
        	$('#QuitarPegarDireccion').remove();
        	$('#PegarDireccion').append("<p id='QuitarPegarDireccion'><strong>Dirección: <br></strong>" + direccion + "</p>");
        	
        },
        error: (e) => {
        	console.log("Error");
        }
      });	
}

function PintarMedicos(item, index)
{
	
		$('#seccionMedicos').append("<div class='col-xl-3 Quitar'>" +
										"<div class='text-center'>" + 
											"<div class='articlexd'>" + 
												"<div class='team-member'>" +
                        							"<div class='image'>" + 		
                        								"<img src='uploads/user.png' alt=' ' class='imagedoctor rounded-circle'>" + 
                        							"</div>" + 
                        							"<h3><a href='team-member.html'>" + item[10] + " " + item[8] + " "+ item[7] + "</a></h3>" +
                        							"<p class='role'>Doctor</p>" +
                        							"<br>" +
                        							"<a data-toggle='modal' data-target='#VerAgendarCita' class='btn btn-primary' style='color:#ffffff' onclick='ObtenerCitas(" + item[0] +")'>Agendar cita</a>" +
                        							"<br>" +
                        							"<br>" +
                        							"<a data-toggle='modal' data-target='#informacion' class='btn btn-danger' style='color:#ffffff' onclick='ObtenerInfo(" + item[0] +")'><i class='fa fa-info-circle'></i> Informaci&oacute;n</a>" +
                        						"</div>" +
                        					"</div>" +
                        				"</div>" +
                        			"</div>");
}

//Aguascalientes = Aguascalientes
//21.894045, -102.324976
//Baja California = Baja California
//31.918608, -116.730909
//Baja California Sur = Baja California Sur
//26.679062, -112.218084
//Campeche = Campeche
//19.238839, -103.761842
//Chiapas = Chiapas
//16.750819, -93.105220
//Chihuahua = Chihuahua
//28.693800, -106.139994
//Ciudad de México = Mexico City
//19.431091, -99.134792
//Coahuila de Zaragoza = Coahuila
//27.851918, -102.532834
//Colima = Colima
//19.238839, -103.761842
//Durango = Durango
//24.785062, -106.214821
//Estado de México = México
//"https://geocode.xyz/19.819357,-99.917600?geoit=json"
//Guanajuato = Guanajuato
//21.015768, -101.261509
//Guerrero = Guerrero
//18.405219, -100.700306
//Estado de Hidalgo = Hidalgo
//20.579027, -99.345064
//Jalisco =  Jalisco
//20.407997, -105.692629
//Michoacán = Michoacán
//18.287096, -103.394867
//Morelos = Morelos
//18.787363, -99.299618
//Nayarit = Nayarit
//21.967101, -105.555907
//Nuevo León = Nuevo León
//25.681927, -100.326660
//Oaxaca = Oaxaca
//17.073243, -96.762485
//Puebla = Puebla
//19.042105, -98.239102
//Querétaro = Querétaro
//20.745356, -100.297710
//Quintana Roo = Quintana Roo
//19.645799, -88.449273
//San Luís Potosí = San Luis Potosí
//22.137460, -101.031313
//Sinaloa = Sinaloa
//24.757671, -107.703331
//Sonora = Sonora
//29.414442, -109.512805
//Tabasco = Tabasco
//18.223730, -93.993328
//Tamaulipas = Tamaulipas
//24.890866, -98.668855
//Tlaxcala = Tlaxcala
//19.441276, -98.404091
//Veracruz = Veracruz
//19.199598, -96.187565
//Yucatán = Yucatán
//21.287522, -89.363221
//Zacatecas = Zacatecas
//23.546667, -103.199474



