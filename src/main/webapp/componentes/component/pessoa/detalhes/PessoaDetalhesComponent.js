
import {conversor} from '../../../../sistema/util/Conversor.js';

import DetalhesComponent from '../../DetalhesComponent.js';
import EnderecoDetalhesComponent from '../../endereco/detalhes/EnderecoDetalhesComponent.js';
import ContatoInfoDetalhesComponent from '../../contato-info/detalhes/ContatoInfoDetalhesComponent.js';

export default class PessoaDetalhesComponent extends DetalhesComponent {
	
	constructor( prefixo, compELIDSufixo ) {
		super( prefixo, 'pessoa-detalhes', compELIDSufixo, 'mensagem_el' );
		
		this.enderecoDetalhesComponent = new EnderecoDetalhesComponent( prefixo, 'endereco_el' );
		this.contatoInfoDetalhesComponent = new ContatoInfoDetalhesComponent( prefixo, 'contato_info_el' );
		
		super.addFilho( this.enderecoDetalhesComponent );
		super.addFilho( this.contatoInfoDetalhesComponent );
	}
	
	carrega( dados ) {
		let data_nasc = conversor.formataDataString( dados.dataNascimento );
		
		super.setHTMLCampoValor( 'nome', 'Logradouro:', dados.nome );						
		super.setHTMLCampoValor( 'nome_social', 'Nome social:', dados.nomeSocial );						
		super.setHTMLCampoValor( 'cpf', 'CPF:', dados.cpf );						
		super.setHTMLCampoValor( 'rg', 'RG:', dados.rg );						
		super.setHTMLCampoValor( 'data_nascimento', 'Data de nascimento:', data_nasc );						

		super.setHTMLCampoValor( 'sexo', 'Sexo:', dados.sexo );						
		super.setHTMLCampoValor( 'estado_civil', 'Estado civil:', dados.estadoCivil );						
		super.setHTMLCampoValor( 'nacionalidade', 'Nacionalidade:', dados.nacionalidade );						
		super.setHTMLCampoValor( 'raca', 'Raça:', dados.raca );						
		super.setHTMLCampoValor( 'religiao', 'Religião:', dados.religiao );						
		
		this.enderecoDetalhesComponent.carrega( dados.endereco );
		this.contatoInfoDetalhesComponent.carrega( dados.contatoInfo );
	}
	
}