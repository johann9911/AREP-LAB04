 function sum()
        {
			var num1=document.getElementById("num1");
            var num2=document.getElementById("num2");
			var resul=document.getElementById("resul");
			var total = (parseInt(num1.value) + parseInt(num2.value));
			
			mostrar(total, resul.value);
			   
        }

 function multiplication()
        {
			var num1=document.getElementById("num1");
            var num2=document.getElementById("num2");
			var resul=document.getElementById("resul");
			var total = (parseInt(num1.value) * parseInt(num2.value));
			
			mostrar(total, resul.value);
			   
        }
	function mostrar(total, comp){
		if(total === parseInt(comp)){
				document.getElementById("spTotal").innerHTML = total;
            	document.getElementById("contenido1").style.display="block";
				document.getElementById("contenido2").style.display="none";
			}else{
				document.getElementById("contenido2").style.display="block";
				document.getElementById("contenido1").style.display="none";
			}
	}