
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
												selectService.carregaDisciplinasPorProfessorSelect( professorId, 'perfil_turmas_disciplinas_select' );												
											}
										} );
									}
								} );
							}
						} );
					}
				} );
			}
		} );
		
		/*
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
		*/
	}
		
	alteraPerfil() {
		sistema.recarregaPaginaCorrente();
	}	
	
	recarrega() {
		this.recarregaComponente();		
	}
		
	getInstituicaoID() {
		let v = this.getFieldValue( 'perfil_instituicao' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
	}
	
	getEscolaID() {
		let v = this.getFieldValue( 'perfil_escola' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
	}
	
	getAnoLetivoID() {
		let v = this.getFieldValue( 'perfil_anoletivo' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
	}
	
	getTurmaID() {
		let v = this.getFieldValue( 'perfil_turma' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
	}
	
	getProfessorID() {
		let v = this.getFieldValue( 'perfil_professor' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
	}
	
	getTurmaDisciplinaID() {
		let v = this.getFieldValue( 'perfil_turma_disciplina' );
		if ( v === undefined || v === null || v === '' )
			return '-1';
		return v;
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
