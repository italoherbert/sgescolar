package sgescolar.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sgescolar.enums.EstadoCivilEnumManager;
import sgescolar.enums.NacionalidadeEnumManager;
import sgescolar.enums.RacaEnumManager;
import sgescolar.enums.ReligiaoEnumManager;
import sgescolar.enums.SexoEnumManager;
import sgescolar.model.Pessoa;
import sgescolar.model.request.SavePessoaRequest;
import sgescolar.model.response.PessoaResponse;
import sgescolar.util.ConversorUtil;

@Component
public class PessoaBuilder {

	@Autowired
	private EnderecoBuilder enderecoBuilder;
	
	@Autowired
	private ContatoInfoBuilder contatoInfoBuilder;
		
	@Autowired
	private EstadoCivilEnumManager estadoCivilEnumConversor;
	
	@Autowired
	private NacionalidadeEnumManager nacionalidadeEnumConversor;
	
	@Autowired
	private SexoEnumManager sexoEnumConversor;
	
	@Autowired
	private RacaEnumManager racaEnumConversor;
	
	@Autowired
	private ReligiaoEnumManager religiaoEnumConversor;
	
	@Autowired
	private ConversorUtil conversorUtil;
	
	public void carregaPessoa( Pessoa p, SavePessoaRequest request ) {								
		p.setNome( request.getNome() );			
		p.setNomeSocial( request.getNomeSocial() );		
		p.setCpf( request.getCpf() );
		p.setRg( request.getRg() ); 
		p.setSexo( sexoEnumConversor.getEnum( request.getSexo() ) );
		p.setEstadoCivil( estadoCivilEnumConversor.getEnum( request.getEstadoCivil() ) );
		p.setNacionalidade( nacionalidadeEnumConversor.getEnum( request.getNacionalidade() ) );
		p.setRaca( racaEnumConversor.getEnum( request.getRaca() ) );
		p.setReligiao( religiaoEnumConversor.getEnum( request.getReligiao() ) );		
		p.setDataNascimento( conversorUtil.stringParaData( request.getDataNascimento() ) );
							
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
				
		resp.setDataNascimento( conversorUtil.dataParaString( p.getDataNascimento() ) );		
		
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
