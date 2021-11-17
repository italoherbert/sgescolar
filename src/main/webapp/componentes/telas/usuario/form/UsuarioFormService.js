import {sistema} from "../../../../../sistema/Sistema.js";

import UsuarioFormComponent2 from './UsuarioFormComponent2.js';

export default class UsuarioFormService {

	constructor() {
		this.formComponent = new UsuarioFormComponent2( 'usuario_form' ); 		
		
	}

	onCarregado() {			
		this.formComponent.configura( {
			usuarioId : this.params.usuarioId,
			op : this.params.op,			
		} );		
						
		this.formComponent.carregaHTML();
	}
	
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/usuario/atualiza/"+this.params.usuarioId;
		} else {
			metodo = "POST";
			url = "/api/usuario/registra";
		}
		
		this.formComponent.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.formComponent.getJSON() ),
			sucesso : function( resposta ) {	
				instance.formComponent.mostraInfo( 'Usuario salvo com êxito.' );																
				instance.formComponent.limpaTudo();
				instance.formComponent.refreshGruposSelects();
				instance.params.op = 'cadastrar';						
			},
			erro : function( msg ) {
				instance.formComponent.mostraErro( msg );	
			}
		} );
	}
	
	adicionarGruposSelecionados() {
		this.formComponent.addGruposSelecionados();
	}
	
	removerGruposSelecionados() {
		this.formComponent.removeGruposSelecionados();
	}
			
	paraUsuariosTela() {
		sistema.carregaPagina( 'usuario-tela' );
	}		

}
export const usuarioForm = new UsuarioFormService();