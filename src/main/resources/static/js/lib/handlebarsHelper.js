function executeHandlebarsTemplate(templateScriptId, htmlContainerId, data) {
	let source = document.getElementById(templateScriptId).innerHTML;
	let template = Handlebars.compile(source);
	var html = template(data);
	document.getElementById(htmlContainerId).innerHTML = html;
}

function loadHandlebarsFromFile(templateScriptName, htmlContainerId, data){
	 $.get('/template/' + templateScriptName, function(source){
		 var template = Handlebars.compile(source);
		 var html = template(data);
		 $('#' + htmlContainerId).html(html);
	 });
}

Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {

    switch (operator) {
        case '==':
            return (v1 == v2) ? options.fn(this) : options.inverse(this);
        case '===':
            return (v1 === v2) ? options.fn(this) : options.inverse(this);
        case '!=':
            return (v1 != v2) ? options.fn(this) : options.inverse(this);
        case '!==':
            return (v1 !== v2) ? options.fn(this) : options.inverse(this);
        case '<':
            return (v1 < v2) ? options.fn(this) : options.inverse(this);
        case '<=':
            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
        case '>':
            return (v1 > v2) ? options.fn(this) : options.inverse(this);
        case '>=':
            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
        case '&&':
            return (v1 && v2) ? options.fn(this) : options.inverse(this);
        case '||':
            return (v1 || v2) ? options.fn(this) : options.inverse(this);
        default:
            return options.inverse(this);
    }
});

//function(templateScript) {
//	let source = document.getElementById("placeDetailsTemplateScript").innerHTML;
//	let template = Handlebars.compile(source);
//	
//	var data = {
//		firstname: "Łukasz",
//		lastname: "Hołdrowicz"
//	};
//	
//	var html = template(data);
//	document.getElementById("handlebarsTemplate").innerHTML = html;
//}