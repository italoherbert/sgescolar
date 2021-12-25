
insert into usuario_grupo ( nome ) values ( 'RAIZ' );
insert into usuario_grupo ( nome ) values ( 'ADMIN' );
insert into usuario_grupo ( nome ) values ( 'SECRETARIO' );
insert into usuario_grupo ( nome ) values ( 'PROFESSOR' );
insert into usuario_grupo ( nome ) values ( 'ALUNO' );

insert into usuario ( username, password, perfil ) values ( 'raiz', sha256( 'raiz' ),'RAIZ' );
update usuario set password=substring( password, 3 ) where username='raiz';

/*
insert into usuario ( username, password, perfil ) values ( 'admin', hash( 'sha256', 'admin', 1 ), 'ADMIN' );
*/

insert into usuario_grupo_map ( usuario_id, grupo_id ) values (
    (select id from usuario where username='raiz'),
    (select id from usuario_grupo where nome='RAIZ')
);

insert into recurso ( nome ) values 
( 'usuario' ),
( 'usuarioGrupo' ),
( 'recurso' ),
( 'permissaoGrupo' ),
( 'pessoa' ),
( 'administrador' ),
( 'aluno' ),
( 'professor' ),
( 'secretario' ),
( 'perfil' ),
( 'config' ),
( 'instituicao' ),
( 'escola' ),
( 'anoLetivo' ),
( 'feriado' ),
( 'periodo' ),
( 'curso' ),
( 'serie' ),
( 'turma' ),
( 'disciplina' ),
( 'horario' ),
( 'listaFrequencia'),
( 'vinculo' ),
( 'matricula' ),
( 'professorAlocacao' ),
( 'avaliacao' ),
( 'planejamento' ),
( 'bnccHabilidade' ),
( 'avaliacaoExterna' );

insert into permissao_grupo ( grupo_id, recurso_id, leitura, escrita, remocao ) values 

( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='usuario'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='usuarioGrupo'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='recurso'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='permissaoGrupo'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='config'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='pessoa'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='administrador'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='secretario'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='professor'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='aluno'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='escola'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='instituicao'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='anoLetivo'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='feriado'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='periodo'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='curso'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='serie'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='turma'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='disciplina'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='horario'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='listaFrequencia'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='vinculo'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='matricula'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='professorAlocacao'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='bnccHabilidade'), true, true, true ),
( (select id from usuario_grupo where nome='RAIZ'), (select id from recurso where nome='avaliacaoExterna'), true, true, true ),


( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='config'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='pessoa'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='administrador'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='secretario'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='professor'), true, false, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='aluno'), true, false, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='escola'), true, true, false ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='instituicao'), true, false, false ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='anoLetivo'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='feriado'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='periodo'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='curso'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='serie'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='turma'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='disciplina'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='horario'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='listaFrequencia'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='vinculo'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='matricula'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='professorAlocacao'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='bnccHabilidade'), true, true, true ),

( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='instituicao'), true, false, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='escola'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='perfil'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='pessoa'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='aluno'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='professor'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='secretario'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='anoLetivo'), true, true, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='feriado'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='periodo'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='curso'), true, false, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='serie'), true, false, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='disciplina'), true, false, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='turma'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='horario'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='listaFrequencia'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='vinculo'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='matricula'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='professorAlocacao'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='avaliacao'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='planejamento'), true, false, false ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='bnccHabilidade'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='avaliacaoExterna'), true, true, true ),


( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='instituicao'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='perfil'), true, true, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='pessoa'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='professor'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='aluno'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='matricula'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='turma'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='curso'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='serie'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='disciplina'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='horario'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='professorAlocacao'), true, true, true ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='anoLetivo'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='listaFrequencia'), true, true, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='avaliacao'), true, true, true ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='planejamento'), true, true, true ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='bnccHabilidade'), true, false, false ),

( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='instituicao'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='perfil'), true, true, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='pessoa'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='aluno'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='matricula'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='perfil'), true, true, true ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='turma'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='curso'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='serie'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='disciplina'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='horario'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='anoLetivo'), true, false, false );