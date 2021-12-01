
import RootFormComponent from '../../../component/RootFormComponent.js';
import TabelaComponent from '../../../component/tabela/TabelaComponent.js';

export default class FrequenciaFormComponent extends RootFormComponent {
	
	frequenciaTabelaCampos = [ 'Aluno', 'Aula', 'Serie', 'Turma', 'Disciplina', 'Presente', 'Modalidade' ];
	
	constructor() {
		super( 'frequencia_form', 'mensagem-el' );
		
		this.tabelaComponent = new TabelaComponent( '', 'frequencia-tabela-el', this.frequenciaTabelaCampos );
		
		super.addFilho( this.tabelaComponent );
	}
	
	carregouHTMLCompleto() {
		let alunoId = this.globalParams.alunoId;
	}
	
} 