
import {sistema} from '../../../../sistema/Sistema.js';

import {selectService} from '../../../service/SelectService.js';

import RootFormComponent from '../../../component/RootFormComponent.js';

export default class PerfilService extends RootFormComponent {
	
	constructor() {
		super( 'perfil_select_form', 'perfil-select-mensagem-el' )
	}
	
	onCarregado() {		
		const instance = this;
		selectService.carregaInstituicoesSelect( 'perfil_instituicoes_select', {
			onchange : () => {
				let instituicaoId = instance.getFieldValue( 'perfil_instituicao' );				 
				selectService.carregaEscolasSelect( instituicaoId, 'perfil_escolas_select', {
					onchange : () => {
						let escolaId = instance.getFieldValue( 'perfil_escola' );						
						selectService.carregaAnosLetivosSelect( escolaId, 'perfil_anosletivos_select', {
							onchange : () => {
								let anoLetivoId = instance.getFieldValue( 'perfil_anoletivo' );								
								selectService.carregaTurmasPorAnoLetivoSelect( anoLetivoId, 'perfil_turmas_select', {
									onchange : () => {
										let turmaId = instance.getFieldValue( 'perfil_turma' );										
										selectService.carregaProfessoresPorTurmaSelect( turmaId, 'perfil_professores_select', {
											onchange : () => {
												let professorId = instance.getFieldValue( 'perfil_professor' );												
												selectService.carregaDisciplinasPorProfessorSelect( professorId, 'perfil_turmas_disciplinas_select', {
													onchange : () => {
														let turmaDisciplinaId = instance.getFieldValue( 'perfil_turma_disciplina' );
														sistema.globalVars.turmaDisciplinaId = turmaDisciplinaId;														
													}
												} );
												sistema.globalVars.professorId = professorId;
											}
										} );
										sistema.globalVars.turmaId = turmaId; 
									}
								} );
								sistema.globalVars.anoLetivoId = anoLetivoId;
							}
						} );
						sistema.globalVars.escolaId = escolaId;
					}
				} );
				sistema.globalVars.instituicaoId = instituicaoId;				
			}
		} );
		
		let el = this.getEL( 'instituicao-perfil-select-field' );
		if ( sistema.globalVars.perfil.name === 'RAIZ' ) {
			el.classList.add( 'd-block' );
			el.classList.add( 'visible' );
		} else {
			el.classList.add( 'd-none' );
			el.classList.add( 'hidden' );
			
			el = this.getEL( 'escola-perfil-select-field' );
			if ( sistema.globalVars.perfil.name === 'ADMIN' ) {
				el.classList.add( 'd-block' );
				el.classList.add( 'visible' );
			} else {
				el.classList.add( 'd-none' );
				el.classList.add( 'hidden' );
			}
		}
	}
		
	alteraPerfil() {
		sistema.recarregaPaginaCorrente();
	}	
		
	getInstituicaoID() {
		return this.getFieldValue( 'perfil_instituicao' );
	}
	
	getEscolaID() {
		return this.getFieldValue( 'perfil_escola' );
	}
	
	getAnoLetivoID() {
		return this.getFieldValue( 'perfil_anoletivo' );
	}
	
	getTurmaID() {
		return this.getFieldValue( 'perfil_turma' );
	}
	
	getProfessorID() {
		return this.getFieldValue( 'perfil_professor' );
	}
	
	getTurmaDisciplinaID() {
		return this.getFieldValue( 'perfil_turma_disciplina' );
	}
	
	setInstituicaoID( id ) {
		this.setFieldValue( 'perfil_instituicao', id );
	}
	
	setEscolaID( id ) {
		this.setFieldValue( 'perfil_escola', id );
	}
	
	setAnoLetivoID( id ) {
		this.setFieldValue( 'perfil_anoletivo', id );
	}
	
	setTurmaID( id ) {
		this.setFieldValue( 'perfil_turma', id );
	}
	
	setProfessorID( id ) {
		this.setFieldValue( 'perfil_professor', id );
	}
	
	setTurmaDisciplinaID( id ) {
		this.setFieldValue( 'perfil_disciplina', id );
	}
		
}
export const perfilService = new PerfilService();
