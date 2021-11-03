insert into usuario_grupo ( perfil ) values ( 'ADMIN' );
insert into usuario_grupo ( perfil ) values ( 'DIRETOR' );
insert into usuario_grupo ( perfil ) values ( 'SECRETARIO' );
insert into usuario_grupo ( perfil ) values ( 'PROFESSOR' );
insert into usuario_grupo ( perfil ) values ( 'ALUNO' );

/*
insert into usuario ( username, password, grupo_id ) values ( 'admin', sha256( 'admin' ), (select id from usuario_grupo where nome='ADMIN') );
update usuario set password=substring( password, 3 ) where username='admin';

insert into usuario ( username, password, grupo_id ) values ( 'diretor', sha256( 'diretor' ), (select id from usuario_grupo where nome='DIRETOR') );
update usuario set password=substring( password, 3 ) where username='diretor';

insert into usuario ( username, password, grupo_id ) values ( 'secretario', sha256( 'secretario' ), (select id from usuario_grupo where nome='SECRETARIO') );
update usuario set password=substring( password, 3 ) where username='secretario';

insert into usuario ( username, password, grupo_id ) values ( 'professor', sha256( 'professor' ), (select id from usuario_grupo where nome='PROFESSOR') );
update usuario set password=substring( password, 3 ) where username='professor';

insert into usuario ( username, password, grupo_id ) values ( 'aluno', sha256( 'aluno' ), (select id from usuario_grupo where nome='ALUNO') );
update usuario set password=substring( password, 3 ) where username='aluno';
*/

insert into usuario ( username, password, grupo_id ) values ( 'admin', hash( 'sha256', 'admin', 1 ), (select id from usuario_grupo where perfil='ADMIN') );
insert into usuario ( username, password, grupo_id ) values ( 'diretor', hash( 'sha256', 'diretor', 1 ), (select id from usuario_grupo where perfil='DIRETOR') );
insert into usuario ( username, password, grupo_id ) values ( 'secretario', hash( 'sha256', 'secretario', 1 ), (select id from usuario_grupo where perfil='SECRETARIO') );
insert into usuario ( username, password, grupo_id ) values ( 'professor', hash( 'sha256', 'professor', 1 ), (select id from usuario_grupo where perfil='PROFESSOR') );
insert into usuario ( username, password, grupo_id ) values ( 'aluno', hash( 'sha256', 'aluno', 1 ), (select id from usuario_grupo where perfil='ALUNO') );

insert into recurso ( nome ) values 
( 'usuario' ),
( 'usuarioGrupo' ),
( 'recurso' ),
( 'permissaoGrupo' ),
( 'escola' ),
( 'pessoa' ),
( 'aluno' ),
( 'professor' ),
( 'secretario' ),
( 'config' );

insert into permissao_grupo ( grupo_id, recurso_id, leitura, escrita, remocao ) values 
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='usuario'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='usuarioGrupo'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='recurso'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='permissaoGrupo'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='config'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='aluno'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='professor'), true, true, true ),
( (select id from usuario_grupo where perfil='ADMIN'), (select id from recurso where nome='secretario'), true, true, true ),

( (select id from usuario_grupo where perfil='SECRETARIO'), (select id from recurso where nome='escola'), true, true, true ),
( (select id from usuario_grupo where perfil='SECRETARIO'), (select id from recurso where nome='pessoa'), true, false, true ),

( (select id from usuario_grupo where perfil='DIRETOR'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where perfil='DIRETOR'), (select id from recurso where nome='pessoa'), true, true, true ),

( (select id from usuario_grupo where perfil='PROFESSOR'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where perfil='PROFESSOR'), (select id from recurso where nome='pessoa'), true, true, false ),

( (select id from usuario_grupo where perfil='ALUNO'), (select id from recurso where nome='escola'), true, false, false ),
( (select id from usuario_grupo where perfil='ALUNO'), (select id from recurso where nome='pessoa'), true, false, false );
