
import {sistema} from '../../sistema/Sistema.js';

export default class FormContent {
	
	form = null;
	
	prefixo = '';

	compId = null;
	compElementoSufixo = null;
	mensagemElementoSufixo = null;
	
	globalParams = {};
	params = {};
	
	parente = null;
	filhos = [];
	
	statusCarregando = false;	
	carregarConteudoHTML = true;	
	
	carregouHTMLCompleto = null;
		
	constructor( prefixo, compId, compElementoSufixo, msgElementoSufixo ) {
		this.prefixo = prefixo;
		this.compId = compId;
		this.compElementoSufixo = compElementoSufixo;
		this.mensagemElementoSufixo = msgElementoSufixo;
	}
	
	isCarregando() {
		if ( this.statusCarregando == true )
			return true;
			
		for( let i = 0; i < this.filhos.length; i++ )
			if ( this.filhos[ i ].isCarregando() === true )
				return true;
		return false;
	}
	
	configura( form, globalParams ) {
		this.form = form;
		this.globalParams = globalParams;
	
		for( let i = 0; i < this.filhos.length; i++ )
			this.filhos[ i ].configura( form, globalParams ); 		
	}		
					
	limpaTudo() {
		if ( typeof( this.limpaForm ) === 'function' )
			this.limpaForm( this );
			
		for( let i = 0; i < this.filhos.length; i++ )
			this.filhos[ i ].limpaTudo(); 
	}		
	
	addFilho( fc ) {
		fc.parente = this;		
		this.filhos.push( fc );
	}
	
	carregaHTML() {
		this.statusCarregando = true;
				
		if ( this.carregarConteudoHTML === true ) {
			this.carregaConteudoHTML();
		} else {		
			for( let i = 0; i < this.filhos.length; i++ )
				this.filhos[ i ].carregaHTML();			
				
			this.statusCarregando = false;
		}
	}
					
	carregaConteudoHTML() {
		this.statusCarregando = true;
		
		let compELID = this.getComponenteELID();
																	
		let instance = this;
		sistema.carregaComponente( this.compId, compELID, Object.assign( {}, this.globalParams, this.params, {	
			prefixo : this.prefixo,
			carregado : ( xmlhttp ) => {																																														
				if ( typeof( instance.onHTMLCarregado ) === 'function' )
					instance.onHTMLCarregado.call( instance, xmlhttp );
																
				for( let i = 0; i < instance.filhos.length; i++ )
					instance.filhos[ i ].carregaHTML();								
													
				instance.statusCarregando = false;
				
				let p = instance;
				let parar = false;
				while( parar === false && p !== null && p !== undefined ) {
					if ( typeof( p.carregouHTMLCompleto ) === 'function' ) {
						if ( p.isCarregando() === false ) {
							p.carregouHTMLCompleto.call( p, xmlhttp );
							parar = true;
						}
					}	
					
					if ( parar === false )
						p = p.parente;										
				}
			},
			houveErro : ( erromsg ) => {
				instance.mostraErro( erromsg );
			}
		} ) );
	}
	
	mostraInfo( info ) {
		let elid = this.getMensagemELID();
		sistema.mostraMensagemInfo( elid, info );
	}
	
	mostraErro( erro ) {
		let elid = this.getMensagemELID();
		sistema.mostraMensagemErro( elid, erro );
	}
	
	mostraAlerta( alerta ) {
		let elid = this.getMensagemELID();
		sistema.mostraMensagemAlerta( elid, alerta );
	}
	
	limpaMensagem() {
		let elid = this.getMensagemELID();
		sistema.limpaMensagem( elid );
	}
			
	getEL( sufixo ) {
		return document.getElementById( this.prefixo + sufixo );
	}
	
	getELID( sufixo ) {
		return this.prefixo + sufixo;
	}
	
	getComponenteELID() {
		return this.prefixo + this.compElementoSufixo; 
	}
	
	getMensagemELID() {
		return this.prefixo + this.mensagemElementoSufixo; 
	}
		
	getField( field ) {		
		return this.form[ this.prefixo + field ];
	}
	
	getFieldValue( field ) {
		return this.form[ this.prefixo + field ].value;
	}
	
	getFieldChecked( field ) {
		return this.form[ this.prefixo + field ].checked;
	}
	
	setFieldValue( field, value ) {		
		this.form[ this.prefixo + field ].value = value;
	}
	
	setFieldChecked( field, checked ) {
		this.form[ this.prefixo + field ].checked = checked;
	}
		
}