
import {sistema} from '../../../../sistema/Sistema.js';

import UsuarioGrupoFormComponent from './UsuarioGrupoFormComponent.js';

export default class UsuarioGrupoFormService {
										
	constructor() {
		this.component = new UsuarioGrupoFormComponent( 'usuario_grupo_form' ); 
	}					
																
	onCarregado() {			
		this.component.configura( {
			usuarioGrupoId : this.params.usuarioGrupoId,
			op : this.params.op,			
		} );
		
		this.component.carregaHTML();																	
	}
	
	sincronizaRecursos() {
		this.component.sincronizaRecursos( this.params.op, this.params.usuarioGrupoId );
	}
					
	salva() {						
		let url;
		let metodo;
		
		if ( this.params.op === 'editar' ) {
			metodo = "PUT";
			url = "/api/usuario/grupo/atualiza/"+this.params.usuarioGrupoId;
		} else {
			metodo = "POST";
			url = "/api/usuario/grupo/registra";
		}
		
		this.component.limpaMensagem();
				
		let instance = this;
		sistema.ajax( metodo, url, {
			cabecalhos : {
				"Content-Type" : "application/json; charset=UTF-8"
			},
			corpo : JSON.stringify( this.component.getJSON() ),
			sucesso : function( resposta ) {	
				instance.component.mostraInfo( 'Grupo de usuario salvo com êxito.' );																
				instance.component.limpaTudo();
				instance.params.op = 'cadastrar';
			},
			erro : function( msg ) {
				instance.component.mostraErro( msg );	
			}
		} );
	}
	
	paraUsuarioGruposTela() {
		sistema.carregaPagina( 'usuario-grupo-tela' );
	}
			
}
export const usuarioGrupoForm = new UsuarioGrupoFormService();