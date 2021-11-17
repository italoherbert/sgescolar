
import {sistema} from '../../sistema/Sistema.js';

export default class Component {
		
	prefixo = '';

	compId = null;
	elid_sufixo = null;
	msgELIDSufixo = null;
	
	globalParams = {};
	params = {};
	
	parente = null;
	filhos = [];
	
	statusCarregando = false;	
	carregarConteudoHTML = true;	
			
	constructor( prefixo, compId, compELIDSufixo, msgELIDSufixo ) {
		this.prefixo = prefixo;
		this.compId = compId;
		this.compELIDSufixo = compELIDSufixo;
		this.msgELIDSufixo = msgELIDSufixo;
	}
	
	isCarregando() {
		if ( this.statusCarregando == true )
			return true;
			
		for( let i = 0; i < this.filhos.length; i++ )
			if ( this.filhos[ i ].isCarregando() === true )
				return true;
		return false;
	}
	
	configura( globalParams ) {
		this.globalParams = globalParams;
	
		for( let i = 0; i < this.filhos.length; i++ )
			this.filhos[ i ].configura( globalParams ); 
			
		if ( typeof( this.onConfigurado ) === 'function' )
			this.onConfigurado.call( this );		
	}		
					
	limpaTudo() {				
		if ( typeof( this.limpaDados ) === 'function' )
			this.limpaDados( this );
									
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
			this.executaCarregouHTMLCompletoSeCarregamentoConcluido();				
		}
	}
					
	carregaConteudoHTML() {
		this.statusCarregando = true;
		
		let compELID = this.getComponenteELID();
																	
		let instance = this;
		sistema.carregaComponente( this.compId, compELID, Object.assign( {}, this.globalParams, this.params, {	
			prefixo : this.prefixo,
			sucesso : ( xmlhttp ) => {																																														
				if ( typeof( instance.onHTMLCarregado ) === 'function' )
					instance.onHTMLCarregado.call( instance, xmlhttp );
																
				for( let i = 0; i < instance.filhos.length; i++ )
					instance.filhos[ i ].carregaHTML();								
													
				instance.statusCarregando = false;
				instance.executaCarregouHTMLCompletoSeCarregamentoConcluido();				
			},
			erro : ( erromsg ) => {
				instance.mostraErro( erromsg );
			}
		} ) );
	}
	
	executaCarregouHTMLCompletoSeCarregamentoConcluido() {
		let p = this;
		let parar = false;
		while( parar === false && p !== null && p !== undefined ) {
			if ( typeof( p.carregouHTMLCompleto ) === 'function' ) {
				if ( p.isCarregando() === false ) {
					p.carregouHTMLCompleto.call( p );
					parar = true;
				}
			}	
			
			if ( parar === false )
				p = p.parente;										
		}
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
		return this.prefixo + this.compELIDSufixo; 
	}
	
	getMensagemELID() {
		return this.prefixo + this.msgELIDSufixo; 
	}
		
	getGlobalParam( sufixo ) {
		return this.globalParams[ this.prefixo + sufixo ];
	}
	
	getParam( sufixo ) {
		return this.params[ this.prefixo + sufixo ];
	}	
	
	setHTML( sufixo, html ) {
		this.getEL( sufixo ).innerHTML = html;
	}
		
}