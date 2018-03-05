// ========== REACT: ==========
//class PlaceDetailsModalClass extends React.Component {
//	render() {
//		let imagesList = [];
//		for(let i = 0 ; i < this.props.currentPlace.images.length ; i++) {
//			imagesList.push('data:image/png;base64, ' + this.props.currentPlace.images[i].image);
//		}
//		
//		let classNameForImagesRow = 'col-lg-' + Math.floor(12 / imagesList.length);
//		
//		let imagesHTML = [];
//		for (let j = 0; j < this.props.currentPlace.images.length; j++) {
//			imagesHTML.push(<div className={classNameForImagesRow}><img className="img-responsive sf-top-margin thumbnail sf-image-test" src={imagesList[j]} /></div>);
//		}
//		
//		return (
//			<div id="placeDetailsModal" className="modal fade" role="dialog">
//				<div className="modal-dialog">
//					<div className="modalContent">
//						<div className="modal-header">
//				        	<button type="button" className="close" data-dismiss="modal">&times;</button>
//				        	<h4 className="modal-title">{this.props.currentPlace.name}</h4>
//				        </div>
//				      
//				        <div className="modal-body">
//				        	<div className="row">
//				        		Opis:
//					        	<p>{this.props.currentPlace.description}</p>
//				        	</div>
//					        <div className="row">
//					        	{imagesHTML}
//					        </div>
//					        <div id="bigImageDiv" className="row" >
//					        </div>
//				        </div>
//				        <div className="modal-footer">
//				        	<button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
//				        </div>
//					</div>
//				</div>
//			</div>
//		);
//	}
//}
//
//function fetchPlaceTest(placeId, modalId) {
//	let baseUrl = $("#base-url").val();
//	let queryUrl = baseUrl + '/places/' + placeId;
//	
//	$.ajax({
//		url: queryUrl,
//		type: 'GET',
//		dataType: 'json',
//		headers: {
//		    'Authorization': 'Basic c3BvdGZpbmRlcjpzcG90ZmluZGVyU2VjcmV0'
//		},
//		success: function(data) {
//			console.log("Place has been downloaded succesfully.");
//			ReactDOM.render(
//				<PlaceDetailsModalClass currentPlace = {data} />,
//				document.getElementById('placeDetailsModalContainer')
//			);
//			$('#' + modalId).modal('show');
//			console.log("juz");
//		}, 
//		error: function(error) {
//			console.log(error);
//		}
//	});
//}

// ========== Handlebars ==========
function fetchPlaceHandlebars(placeId, divToPlaceId) {
	let baseUrl = $("#base-url").val();
	let queryUrl = baseUrl + '/places/' + placeId;
	
	$.ajax({
		url: queryUrl,
		type: 'GET',
		dataType: 'json',
		headers: {
		    'Authorization': 'Basic c3BvdGZpbmRlcjpzcG90ZmluZGVyU2VjcmV0'
		},
		success: function(data) {
			loadHandlebarsFromFile("placeDetails.hbs", divToPlaceId, data);
		}, 
		error: function(error) {
			console.log(error);
		}
	});
}
