
export default class ConfirmModalManager {
						
	htmlpag = null;
	
	constructor( htmlpag ) {
		this.htmlpag = htmlpag;
	}					
												
	carregaModal( elid, params ) {							
		let pagina = this.htmlpag;
		if ( pagina === undefined || pagina === null )
			throw "Pagina não encontrada: Metodo= ConfirmModalManager.carrega( htmlpag )";
			
		if ( params === undefined || params === null )
			throw "Parâmetros do modal não definidos.";
						
		if ( params.titulo === undefined || params.titulo === null )
			throw "Titulo de modal não informado. 'titulo'";				
		if ( params.msg === undefined || params.msg === null )
			throw "Mensagem de modal não definida. 'msg'";
		if ( params.confirm === undefined || params.confirm === null )
			throw "A definição da função de confirmar é obrigatória. 'confirm'";
		if ( params.confirm.texto === undefined || params.confirm.texto === null )
			throw "O texto de confirmação é obrigatório. 'confirm.texto'";
		if ( params.confirm.bt === undefined || params.confirm.bt === null )
			throw "A configuração do botão de confirmar é obrigatória. 'confirm.bt'";
		if ( params.confirm.bt.rotulo === undefined || params.confirm.bt.rotulo === null )
			throw "O rótulo do botão de confirmação é obrigatório. 'confirm.bt.rotulo'";
		if ( params.confirm.bt.onclick === undefined || params.confirm.bt.onclick === null )
			throw "A configuração do evento de confirmação é obrigatória. 'confirm.bt.onclick'";
		if ( params.confirm.bt.onclick.func === undefined || params.confirm.bt.onclick.func === null )
			throw "A função do evento de confirmação é obrigatória. 'confirm.bt.onclick.func'";		
					
		const instance = this;	
				
		params.ids = { 
			modal : 'id-'+new Date().getTime(), 
			botoes : {
				confirm : 'bt-'+new Date().getTime()+1,
				cancelar : 'bt-'+new Date().getTime()+2,
				fechar : 'bt-'+new Date().getTime()+3
			},
			msg : 'msg-'+new Date().getTime(),
		};
		
		params.names = {
			confirm : {
				texto : '___'+params.confirm.texto+'___'
			}
		};
						
		ajaxCarregaHTML( elid, pagina, Object.assign( params, {
			sucesso : (xmlhttp, html) => {				
				instance.showHide( params.ids.modal );
								
				let bt = document.body.querySelector( "#"+params.ids.botoes.confirm );
				bt.addEventListener( "click", (event) => {
					let confirmTexto = document.getElementsByName( params.names.confirm.texto )[0].value;
					if ( confirmTexto === params.confirm.texto ) { 
						instance.showHide( params.ids.modal );
						
						if ( typeof( params.confirm.bt.onclick.func ) == "function" ) {
							let thisref = this;
							if ( params.confirm.bt.onclick.thisref !== undefined && params.confirm.bt.onclick.thisref !== null )
								thisref = params.confirm.bt.onclick.thisref;
							params.confirm.bt.onclick.func.call( thisref, params.confirm.bt.onclick.params );
						}						
					} else {
						let msgEL = document.getElementById( params.ids.msg ); 
						msgEL.innerHTML = "<div class='alert alert-danger'>Você não informou o texto de confirmação acima</div>";
					}
				} );
				
				let fecharFunc = ( event ) => {
					instance.showHide( params.ids.modal );
				};				
				
				document.body.querySelector( "#"+params.ids.botoes.cancelar ).addEventListener( "click", fecharFunc );
				document.body.querySelector( "#"+params.ids.botoes.fechar ).addEventListener( "click", fecharFunc );				
			},
			erro : (xmlhttp) => {
				if ( typeof( params.erro ) == "function" )
					params.erro.call( this, xmlhttp );				
			}
		} ) );		
	}	
	
	showHide( id ) {
		let el = document.body.querySelector( "#"+id );
		el.classList.toggle( "d-block" );
		el.classList.toggle( "d-none" );
		el.classList.toggle( "visible" );
		el.classList.toggle( "hidden" );
	}
	
}