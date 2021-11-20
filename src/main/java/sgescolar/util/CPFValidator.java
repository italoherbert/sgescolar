package sgescolar.util;

import org.springframework.stereotype.Component;

@Component
public class CPFValidator {
		
	public boolean cpfValido( String cpf ) {
		int[] algarismos = this.extraiCpfAlgarismos( cpf );
		if ( algarismos == null )
			return false;
		
		return this.validaCpf( algarismos );
	}
		
	public boolean validaCpf( int[] algarismos ) {		
		boolean iguais = true;
		for( int i = 0; iguais && i < algarismos.length; i++ )
			for( int j = i+1; iguais && j < algarismos.length; j++ )
				if ( algarismos[ i ] != algarismos[ j ] )
					iguais = false;
		
		if ( iguais == true )
			return false;
				
		int soma = 0;
		int peso, i;
		for( i = 0, peso = 10; peso >= 2; i++, peso-- )
			soma += algarismos[ i ] * peso;
				
		int resto = (soma * 10) % 11;
		if ( resto == 10 )
			resto = 0;
		
		if ( resto != algarismos[ algarismos.length - 2 ] )
			return false;
		
		soma = 0;
		i = 0;
		for( i = 0, peso = 11; peso >= 2; i++, peso-- )
			soma += algarismos[ i ] * peso;
		
		resto = (soma * 10) % 11;
		if ( resto == 10 )
			resto = 0;
		
		if ( resto != algarismos[ algarismos.length - 1 ] )
			return false;
		
		return true;
	}
	
	public int[] extraiCpfAlgarismos( String cpf ) {
		boolean maskOk = cpf.matches( "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}" );
		if ( !maskOk )
			return null;
		
		String[] lista = cpf.split( "[\\.-]" );
		String str = "";
		for( String s : lista ) {
			int len = s.length();
			for( int i = 0; i < len; i++ )
				str += s.charAt( i );			
		}
		
		int[] algarismos = new int[ str.length() ];
		for( int i = 0; i < algarismos.length; i++ )
			algarismos[ i ] = Integer.parseInt( String.valueOf( str.charAt( i ) ) );
		
		return algarismos;
	}
	
}
