
class Sistema {
			
	componentes = {}	
	globalVars = {
		usuarioLogado : {},
		token : null,
		logado : false
	}
	
	constructor( componentes ) {
		this.componentes = componentes;
	}
			
	carregaLayout( compID, params ) {
		this.carregaComponente( compID, "layout", params );
	}
		
	carregaPagina( compID, params ) {
		this.carregaComponente( compID, "pagina", params );
	}
	
	carregaConfirmModal( elid, params ) {
		this.carregaConfirmModalComponente( 'confirm-modal', elid, params );
	}	
			
	mostraMensagem( id, tipo, msg ) {		
		let className = ( tipo === 'info' ? 'alert-primary' : 'alert-danger' );
		document.getElementById( id ).innerHTML = "<div class='alert "+className+"'>"+msg+"</div>";
		
		this.scrollTo( id );	
	}
	
	limpaMensagem( id ) {
		document.getElementById( id ).innerHTML = "";
	}
	
	scrollTo( strEL ) {
		let el = document.querySelector( "#"+strEL );
		if ( el )
			el.scrollIntoView();			
	}
		
	showHide( elid ) {
		let el = document.getElementById( elid );
		if ( el === undefined || el === null )
			throw "Elemento HTML não encontrado pelo ID. ID="+elid;
		
		el.classList.toggle( "d-block" );
		el.classList.toggle( "d-none" );
		el.classList.toggle( "visible" );
		el.classList.toggle( "hidden" );
	}
	
	formataData( date ) {
		if ( date === null || date === undefined || date === '' )
			return "Formato de data não reconhecido";
		
		return moment( date ).format( "DD/MM/YYYY" );
	}
	
	formataReal( valor ) {
		let v = parseFloat( parseFloat( valor ).toFixed( 2 ) );
		if ( v === 0 ) 
			return "R$ 0,00";
				
		if ( Math.abs( v ) >= 1.0 ) {
			let n = parseInt( v * 100 );
			let s = ""+n;
			return "R$ "+s.substring( 0, s.length-2 ) + ',' + s.substring( s.length-2, s.length );		
		} else {
			return "R$ " +( ( ""+v ).replace( '.', ',' ) );			
		}				
	}
	
	valorFloat( valor ) {
		if ( isNaN( (""+valor).replace( ',', '.' ) ) === true )
			return valor;
		
		if ( (""+valor) === "0" ) 
			return 0;
		
		return parseFloat( parseFloat( (""+valor).replace( ',', '.' ) ).toFixed( 2 ) );
	}
	
	formataFloat( valor ) {
		if ( (""+valor) === "0" ) 
			return "0";
		
		let v = (""+valor).split( '.' );
		
		let frac = 0;
		if ( v.length === 2 )
			frac = parseInt( v[1] );
		
		return ( frac === 0 ? v[0] : v[0]+','+v[1] );				
	}
	
	carregaComponente( compID, elid, props ) {
		let comp = this.componentes[ compID ];
		if ( comp === undefined || comp === null )
			throw "Componente não encontrado: "+compID;
		
		let pagina = comp.pagina;
		if ( pagina === undefined || pagina === null )
			throw "Pagina não encontrada: "+pagina;
			
		let jsObj = comp.jsObj;
																					
		ajaxCarregaHTML( elid, pagina, {
			sucesso : function() {				
				if ( jsObj !== undefined && jsObj !== null ) {
					if ( props === undefined || props === null ) {
						jsObj.props = {};
					} else {						
						jsObj.props = props;
					}
					
					if ( typeof( jsObj.onCarregado ) === "function" )
						jsObj.onCarregado.call( jsObj );
				}
			},
			erro : function( xmlhttp ) {
				throw "Pagina não encontrada: "+pagina;
			}
		} )
	}
	
	carregaConfirmModalComponente( compID, elid, params ) {		
		let comp = this.componentes[ compID ];
		if ( comp === undefined || comp === null )
			throw "Componente não encontrado: "+compID;
			
		let pagina = comp.pagina;
		if ( pagina === undefined || pagina === null )
			throw "Pagina não encontrada: "+pagina;
			
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
			msg : 'msg-'+new Date().getTime()
		};
		
		params.names = {
			confirm : {
				texto : '___'+params.confirm.texto+'___'
			}
		};
		
		params.sucesso = (xmlhttp, html) => {				
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
					instance.mostraMensagem( params.ids.msg, 'erro', 'Você não informou o texto de confirmação acima.' );
				}
			} );
			
			let fecharFunc = ( event ) => {
				instance.showHide( params.ids.modal );
			};				
			
			document.body.querySelector( "#"+params.ids.botoes.cancelar ).addEventListener( "click", fecharFunc );
			document.body.querySelector( "#"+params.ids.botoes.fechar ).addEventListener( "click", fecharFunc );				
		};
		
		params.erro = (xmlhttp) => {
			if ( typeof( params.erro ) == "function" )
				params.erro.call( this, xmlhttp );				
		};
				
		ajaxCarregaHTML( elid, pagina, params );		
	}
		
	ajax( metodo, url, params ) {	
		if ( this.globalVars[ 'logado' ] === true ) {	 
			if ( params.cabecalhos === undefined || params.cabecalhos === null )
				params.cabecalhos = {};
			params.cabecalhos['Authorization'] = "Bearer "+sistema.globalVars.token;
		}
		
		const instance = this;
					
		params.respostaChegou = function( xmlhttp ) {
			switch ( xmlhttp.status ) {
				case 200:
					if ( typeof( params.sucesso ) == 'function' )
						params.sucesso.call( this, xmlhttp.responseText );
					break; 			
				case 400:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, JSON.parse( xmlhttp.responseText ).mensagem );
					break;
				case 401:
				case 403:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Você não tem permissões suficientes para acessar o recurso requisitado.");
					break;
				case 404:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Recurso não encontrado." );
					break;
				case 500:
					if ( typeof( params.erro ) == 'function' )
						params.erro.call( this, "Erro interno no servidor." );
					break;
			}
			instance.showHide( 'carregando' );
		}
		
		this.showHide( 'carregando' );
		
		ajax( metodo, url, params );	
	}
		
}

