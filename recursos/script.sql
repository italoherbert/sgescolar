insert into usuario_grupo ( nome ) values ( 'ADMIN' );
insert into usuario_grupo ( nome ) values ( 'SECRETARIO' );
insert into usuario_grupo ( nome ) values ( 'PROFESSOR' );
insert into usuario_grupo ( nome ) values ( 'ALUNO' );

/*
insert into usuario ( username, password, grupo_id ) values ( 'admin', sha256( 'admin' ), (select id from usuario_grupo where nome='ADMIN') );
update usuario set password=substring( password, 3 ) where username='admin';

insert into usuario ( username, password, grupo_id ) values ( 'secretario', sha256( 'secretario' ), (select id from usuario_grupo where nome='SECRETARIO') );
update usuario set password=substring( password, 3 ) where username='secretario';

insert into usuario ( username, password, grupo_id ) values ( 'professor', sha256( 'professor' ), (select id from usuario_grupo where nome='PROFESSOR') );
update usuario set password=substring( password, 3 ) where username='professor';

insert into usuario ( username, password, grupo_id ) values ( 'aluno', sha256( 'aluno' ), (select id from usuario_grupo where nome='ALUNO') );
update usuario set password=substring( password, 3 ) where username='aluno';
*/

insert into usuario ( username, password, perfil ) values ( 'admin', hash( 'sha256', 'admin', 1 ), 'ADMIN' );

insert into usuario_grupo_map ( usuario_id, grupo_id ) values (
    (select id from usuario where username='admin'),
    (select id from usuario_grupo where nome='ADMIN')
);

insert into recurso ( nome ) values 
( 'usuario' ),
( 'usuarioGrupo' ),
( 'recurso' ),
( 'permissaoGrupo' ),
( 'pessoa' ),
( 'escola' ),
( 'aluno' ),
( 'professor' ),
( 'perfil' ),
( 'secretario' ),
( 'config' ),
( 'instituicao' ),
( 'anoLetivo' ),
( 'periodoLetivo')
( 'feriado' );

insert into permissao_grupo ( grupo_id, recurso_id, leitura, escrita, remocao ) values 
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='usuario'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='usuarioGrupo'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='recurso'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='permissaoGrupo'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='config'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='pessoa'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='secretario'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='escola'), true, true, true ),
( (select id from usuario_grupo where nome='ADMIN'), (select id from recurso where nome='instituicao'), true, true, true ),

( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='escola'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='pessoa'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='aluno'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='professor'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='secretario'), true, true, true ),
( (select id from usuario_grupo where nome='SECRETARIO'), (select id from recurso where nome='perfil'), true, true, true ),

( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='pessoa'), true, true, true ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='aluno'), true, true, false ),
( (select id from usuario_grupo where nome='PROFESSOR'), (select id from recurso where nome='perfil'), true, true, true ),

( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where nome='ALUNO'), (select id from recurso where nome='perfil'), true, true, true );
