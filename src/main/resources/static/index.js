/**
 * 
 */

function afficheListeClients() {
    fetch('http://localhost:8080/clients')
        .then(function (response) {
            // traiter la réponse
            if (!response.ok) {
                throw Error(response.statusText);
            }
            // lecture du corps de la réponse en tant que JSON.
            return response.json();
        })
        .then(function (responseAsJson) {
            // traitement de l'objet
            console.log(responseAsJson);
            var cpt = 0;

            var listeClients = responseAsJson.map(function(element){
                return element;
            });
            console.log(listeClients);
            var selectClient = document.getElementById("clients");
           
            
            for (var prop in listeClients) {
            	
                console.log(listeClients[prop].nom + " " + listeClients[prop].prenoms);
               
                selectClient[selectClient.options.length] = new Option(listeClients[prop].nom+" "+listeClients[prop].prenoms,listeClients[prop].uuid);
            }


        })
        .catch(function (error) {
            console.log('Il semble avoir un souci avec l affichage des clients...', error);
        });
}


function AfficheChambres(){
    fetch('http://localhost:8080/chambres')
    .then(function (response) {
        // traiter la réponse
        if (!response.ok) {
            throw Error(response.statusText);
        }
        // lecture du corps de la réponse en tant que JSON.
        return response.json();
    })
    .then(function (responseAsJson) {
        // traitement de l'objet
        console.log(responseAsJson);
        var cpt = 0;

        var listeChambres = responseAsJson.map(function(element){
            return element;
        });
        console.log(listeChambres);
      
        for (var prop in listeChambres) {
        
			var checkChambre = document.createElement('input');
			checkChambre.setAttribute('type', 'checkbox');
			checkChambre.setAttribute('class', 'form-check-input');
			checkChambre.setAttribute('id', listeChambres[prop].uuid);
			checkChambre.setAttribute('name', listeChambres[prop].uuid);
			var labelChambre = document.createElement('label');
			labelChambre.setAttribute("for",listeChambres[prop].uuid);
			labelChambre.setAttribute("style","display:block");
			labelChambre.innerHTML= listeChambres[prop].numero+" Surface : "+listeChambres[prop].surfaceEnM2+" m2";
			document.getElementById('divChambres').appendChild(checkChambre);
			document.getElementById('divChambres').appendChild(labelChambre);
       
        }
       
        document.getElementById("hotel").innerHTML = listeChambres[prop].hotel.nom+" -  Etoiles : "+listeChambres[prop].hotel.nombreEtoiles;

    })
    .catch(function (error) {
        console.log('Il semble avoir un souci avec l affichage des chambres...', error);
    });
}

function ajouterReservation(){
	
	var bool = true;
	var listeElementManquant = "";
	var alertDate="";
	var dateDebut = document.getElementById("dateDebut").value;
	var dateFin = document.getElementById("dateFin").value;
	var idClient = document.getElementById("clients").options[document.getElementById("clients").options.selectedIndex].value;
	
	if(idClient===""){
		
		listeElementManquant = listeElementManquant+"- un client\n";
		bool=false;
	}
	
	if(dateDebut===""){
		listeElementManquant = listeElementManquant+"- une date de début\n";
		bool=false;
	}
	
	if(dateFin===""){
		listeElementManquant = listeElementManquant+"- une date de fin\n";
		bool=false;
	}
	
	
	if(dateDebut > dateFin){
		alertDate = "La date de fin doit être supérieure à la date de début";
		bool=false;
	}
	
	var formCheckbox = document.forms['newReservation'].elements; 
	var listeChambres=[];
	
	for (i=0 ; i<= formCheckbox.length-1 ; i++)
	{
		if (formCheckbox[i].type === 'checkbox' && formCheckbox[i].checked)
		{
		
			listeChambres.push(formCheckbox[i].id);
		}
	}
	
	if(listeChambres===""){
		listeElementManquant = listeElementManquant+"- chambre(s)\n";
		bool=false;
	}
	
	if(bool ===false){
		
		var messageAlert = "";
		
		if(listeElementManquant!=""){
			 messageAlert ="Veuillez saisir l' élément manquant :\n";
			 
			if(listeElementManquant.split("-").length > 2){
				 messageAlert ="Veuillez saisir les éléments manquants :\n";
			}
		}	
		alert(messageAlert+listeElementManquant+"\n"+alertDate);
	}
	
	if(bool===true){
		
		console.log("chambre "+listeChambres);
		fetch('http://localhost:8080/reservations', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ dateDebut:dateDebut, dateFin:dateFin, clientId:idClient, chambres:listeChambres})
        }).then(res => res.json())
            .then(res => console.log(res));
        
	}
	
}

