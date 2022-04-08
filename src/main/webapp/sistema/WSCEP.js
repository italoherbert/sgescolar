
import * as elutil from '../../../../sistema/util/elutil.js'
import * as ajax from '../../../../sistema/util/ajax.js'
	
import {sistema} from '../../../../sistema/Sistema.js'	
	
export default class WSCEP {
	
	ufsMap = {
		'AC' : 'Acre',
		'AL' : 'Alagoas',
		'AP' : 'Amapá',
		'AM' : 'Amazonas',
		'BA' : 'Bahia',
		'CE' : 'Ceará',
		'ES' : 'Espírito Santo',
		'GO' : 'Goiás',
		'MA' : 'Maranhão',
		'MT' : 'Mato Grosso',
		'MS' : 'Mato Grosso do Sul',
		'MG' : 'Minas Gerais',
		'PA' : 'Pará',
		'PB' : 'Paraíba',
		'PR' : 'Paraná',
		'PE' : 'Pernambuco',
		'PI' : 'Piauí',
		'RJ' : 'Rio de Janeiro',
		'RN' : 'Rio Grande do Norte',
		'RS' : 'Rio Grande do Sul',
		'RO' : 'Rondônia',
		'RR' : 'Roraima',
		'SC' : 'Santa Catarina',
		'SP' : 'São Paulo',
		'SE' : 'Sergipe',
		'TO' : 'Tocantins'
	};
	
	consultaCEP( cep, params ) {	
		const instance = this;
		
		elutil.showHide( 'carregando' );
			
		ajax.ajaxGetRecurso( 'https://viacep.com.br/ws/'+cep+'/json/', {
			sucesso : ( xmlhttp ) => {
				let dados = JSON.parse( xmlhttp.responseText );
				
				dados.uf = instance.ufsMap[ dados.uf ];
				
				if ( typeof( params.houveSucesso ) === 'function' )
					params.houveSucesso.call( this, dados );
								
				elutil.showHide( 'carregando' );
			},
			erro : ( xmlhttp ) => {
				if ( typeof( params.houveErro ) === 'function' )
					params.houveErro.call( this, sistema.erroMensagem( xmlhttp ) )
				elutil.showHide( 'carregando' );
			}
		} );
		
	} 		
}
export const wsCEP = new WSCEP();