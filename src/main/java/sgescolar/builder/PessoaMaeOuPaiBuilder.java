package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.exception.BooleanFormatoException;
import sgescolar.exception.DataNascFormatoException;
import sgescolar.exception.EstadoCivilNaoReconhecidoException;
import sgescolar.exception.FalecidoInfoFormatoException;
import sgescolar.exception.NacionalidadeNaoReconhecidoException;
import sgescolar.exception.RacaNaoReconhecidoException;
import sgescolar.exception.ReligiaoNaoReconhecidoException;
import sgescolar.exception.SexoNaoReconhecidoException;
import sgescolar.model.PessoaMaeOuPai;
import sgescolar.model.request.SavePessoaMaeOuPaiRequest;
import sgescolar.model.response.PessoaMaeOuPaiResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PessoaMaeOuPaiBuilder {

	@Autowired
	private PessoaBuilder pessoaBuilder;
	
	@Autowired
	private ConversorUtil booleanUtil;
	
	public void carregaPessoaMaeOuPai( PessoaMaeOuPai p, SavePessoaMaeOuPaiRequest request ) 
			throws DataNascFormatoException, 
				SexoNaoReconhecidoException, 
				NacionalidadeNaoReconhecidoException, 
				EstadoCivilNaoReconhecidoException, 
				RacaNaoReconhecidoException, 
				ReligiaoNaoReconhecidoException, 
				FalecidoInfoFormatoException {
		
		try {
			p.setFalecido( booleanUtil.stringParaBoolean( request.getFalecido() ) );
		} catch (BooleanFormatoException e) {
			throw new FalecidoInfoFormatoException();
		}
		
		pessoaBuilder.carregaPessoa( p.getPessoa(), request.getPessoa() ); 
	}
	
	public void carregaPessoaMaeOuPaiResponse( PessoaMaeOuPaiResponse resp, PessoaMaeOuPai p ) {
		resp.setId( p.getId() );
		resp.setFalecido( booleanUtil.booleanParaString( p.isFalecido() ) ); 
		
		pessoaBuilder.carregaPessoaResponse( resp.getPessoa(), p.getPessoa() );
	}
	
	public PessoaMaeOuPai novoPessoaMaeOuPai() {
		PessoaMaeOuPai p = new PessoaMaeOuPai();
		p.setPessoa( pessoaBuilder.novoPessoa() );
		return p;
	}
	
	public PessoaMaeOuPaiResponse novoPessoaMaeOuPaiResponse() {
		PessoaMaeOuPaiResponse resp = new PessoaMaeOuPaiResponse();
		resp.setPessoa( pessoaBuilder.novoPessoaResponse() ); 
		return resp;
	}
	
}
