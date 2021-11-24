create or replace function lower_unaccent( varchar ) returns varchar as $$
begin
    return translate( lower($1), 'âãáàêéèíìôõóòúùüçÇ', 'aaaaeeeiioooouuucC' );
end;
$$ language plpgsql;