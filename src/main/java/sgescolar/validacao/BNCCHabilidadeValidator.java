package sgescolar.validacao;

import org.springframework.stereotype.Component;

import sgescolar.model.request.SaveBNCCHabilidadeRequest;
import sgescolar.model.request.filtro.FiltraBNCCHabilidadesRequest;
import sgescolar.msg.ValidacaoErro;

@Component
public class BNCCHabilidadeValidator {
			
	public void validaSaveRequest( SaveBNCCHabilidadeRequest request ) throws ValidacaoException {
		if ( request.getCodigo() == null )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_CODIGO_OBRIGATORIO );
		if ( request.getCodigo().isBlank() )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_CODIGO_OBRIGATORIO );		
		 
		if ( request.getHabilidade() == null )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_OBRIGATORIA );
		if ( request.getHabilidade().isBlank() )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_OBRIGATORIA );		
	}
	
	public void validaFiltroRequest( FiltraBNCCHabilidadesRequest request ) throws ValidacaoException {
		if ( request.getCodigoIni() == null )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_CODIGO_OBRIGATORIO );
		if ( request.getCodigoIni().isBlank() )
			throw new ValidacaoException( ValidacaoErro.BNCC_HABILIDADE_CODIGO_OBRIGATORIO );		
		
	}
		
}


