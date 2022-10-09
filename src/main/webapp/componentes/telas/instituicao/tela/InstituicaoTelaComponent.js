
import RootFormComponent from '../../../component/RootFormComponent.js';

export default class InstituicaoTelaComponent extends RootFormComponent {
	
	onChangeInstituicao = () => {};
	
	constructor() {
		super( 'instituicao_filtro_form', 'mensagem-el' );
	}
			
	getJSON() {
		return {
			cnpj : super.getFieldValue( 'cnpj' ),
			razaoSocialIni : super.getFieldValue( 'razaosocialini' )
		}
	}
		
}