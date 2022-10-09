
import RootFormComponent from '../../component/RootFormComponent.js';
import HorarioComponent from '../../component/horario/HorarioComponent.js';

export default class AlunoHorarioComponent extends RootFormComponent {
	
	horarioTabelaCampos = [ 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta' ];
	
	constructor() {
		super( 'horario_form', 'mensagem-el' );	
		
		this.horarioComponent = new HorarioComponent( '', 'horario-tabela-el', this.horarioTabelaCampos );
		
		super.addFilho( this.horarioComponent );			
	}
		
	carregaJSON( horario ) {
		this.horarioComponent.carregaPorHorarioJSON( horario );
	}
		
}