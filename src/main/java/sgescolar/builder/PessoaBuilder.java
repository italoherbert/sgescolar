package sgescolar.builder;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.exception.DataNascFormatoException;
import sgescolar.exception.EstadoCivilNaoReconhecidoException;
import sgescolar.exception.NacionalidadeNaoReconhecidoException;
import sgescolar.exception.RacaNaoReconhecidoException;
import sgescolar.exception.ReligiaoNaoReconhecidoException;
import sgescolar.exception.SexoNaoReconhecidoException;
import sgescolar.model.Pessoa;
import sgescolar.model.request.SavePessoaRequest;
import sgescolar.model.response.PessoaResponse;
import sgescolar.util.DataUtil;
import sgescolar.util.enums.EstadoCivilEnumConversor;
import sgescolar.util.enums.NacionalidadeEnumConversor;
import sgescolar.util.enums.RacaEnumConversor;
import sgescolar.util.enums.ReligiaoEnumConversor;
import sgescolar.util.enums.SexoEnumConversor;

@Component
public class PessoaBuilder {

	@Autowired
	private EnderecoBuilder enderecoBuilder;
	
	@Autowired
	private ContatoInfoBuilder contatoInfoBuilder;
	
	@Autowired
	private DataUtil dataUtil;
	
	@Autowired
	private EstadoCivilEnumConversor estadoCivilEnumConversor;
	
	@Autowired
	private NacionalidadeEnumConversor nacionalidadeEnumConversor;
	
	@Autowired
	private SexoEnumConversor sexoEnumConversor;
	
	@Autowired
	private RacaEnumConversor racaEnumConversor;
	
	@Autowired
	private ReligiaoEnumConversor religiaoEnumConversor;
	
	public void carregaPessoa( Pessoa p, SavePessoaRequest request ) 
			throws DataNascFormatoException, 
				SexoNaoReconhecidoException, 
				NacionalidadeNaoReconhecidoException,
				EstadoCivilNaoReconhecidoException,
				RacaNaoReconhecidoException, 
				ReligiaoNaoReconhecidoException {
				
		p.setNome( request.getNome() );			
		p.setNomeSocial( request.getNomeSocial() );		
		p.setCpf( request.getCpf() );
		p.setRg( request.getRg() ); 
		p.setSexo( sexoEnumConversor.getEnum( request.getSexo() ) );
		p.setEstadoCivil( estadoCivilEnumConversor.getEnum( request.getEstadoCivil() ) );
		p.setNacionalidade( nacionalidadeEnumConversor.getEnum( request.getNacionalidade() ) );
		p.setRaca( racaEnumConversor.getEnum( request.getRaca() ) );
		p.setReligiao( religiaoEnumConversor.getEnum( request.getReligiao() ) );
		
		try {
			p.setDataNascimento( dataUtil.stringParaData( request.getDataNascimento() ) );
		} catch (ParseException e) {
			throw new DataNascFormatoException();
		}
					
		enderecoBuilder.carregaEndereco( p.getEndereco(), request.getEndereco() );
		contatoInfoBuilder.carregaContatoInfo( p.getContatoInfo(), request.getContatoInfo() );
	}
	
	public void carregaPessoaResponse( PessoaResponse resp, Pessoa p ) {
		resp.setNome( p.getNome() );			
		resp.setNomeSocial( p.getNomeSocial() );		
		resp.setCpf( p.getCpf() );
		resp.setRg( p.getRg() ); 
		resp.setSexo( sexoEnumConversor.getString( p.getSexo() ) );
		resp.setEstadoCivil( estadoCivilEnumConversor.getString( p.getEstadoCivil() ) );
		resp.setNacionalidade( nacionalidadeEnumConversor.getString( p.getNacionalidade() ) );
		resp.setRaca( racaEnumConversor.getString( p.getRaca() ) );
		resp.setReligiao( religiaoEnumConversor.getString( p.getReligiao() ) );
				
		resp.setDataNascimento( dataUtil.dataParaString( p.getDataNascimento() ) );		
		
		enderecoBuilder.carregaEnderecoResponse( resp.getEndereco(), p.getEndereco() );
		contatoInfoBuilder.carregaContatoInfoResponse( resp.getContatoInfo(), p.getContatoInfo() );
	}
	
	public Pessoa novoPessoa() {
		Pessoa p = new Pessoa();
		p.setEndereco( enderecoBuilder.novoEndereco() );
		p.setContatoInfo( contatoInfoBuilder.novoContatoInfo() );
		return p;
	}
	
	public PessoaResponse novoPessoaResponse() {
		PessoaResponse resp = new PessoaResponse();
		resp.setEndereco( enderecoBuilder.novoEnderecoResponse() );
		resp.setContatoInfo( contatoInfoBuilder.novoContatoInfoResponse() ); 
		return resp;
	}
	
}
