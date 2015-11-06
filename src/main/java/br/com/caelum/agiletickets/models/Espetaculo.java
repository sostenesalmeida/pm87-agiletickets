package br.com.caelum.agiletickets.models;

import static com.google.common.collect.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

@Entity
public class Espetaculo {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((estabelecimento == null) ? 0 : estabelecimento.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sessoes == null) ? 0 : sessoes.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Espetaculo other = (Espetaculo) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (estabelecimento == null) {
			if (other.estabelecimento != null)
				return false;
		} else if (!estabelecimento.equals(other.estabelecimento))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sessoes == null) {
			if (other.sessoes != null)
				return false;
		} else if (!sessoes.equals(other.sessoes))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoDeEspetaculo tipo;

	@ManyToOne
	private Estabelecimento estabelecimento;
	
	@OneToMany(mappedBy="espetaculo")
	private List<Sessao> sessoes = newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDeEspetaculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeEspetaculo tipo) {
		this.tipo = tipo;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}
	
	public List<Sessao> getSessoes() {
		return sessoes;
	}
	
	/**
     * Esse metodo eh responsavel por criar sessoes para
     * o respectivo espetaculo, dado o intervalo de inicio e fim,
     * mais a periodicidade.
     * 
     * O algoritmo funciona da seguinte forma:
     * - Caso a data de inicio seja 01/01/2010, a data de fim seja 03/01/2010,
     * e a periodicidade seja DIARIA, o algoritmo cria 3 sessoes, uma 
     * para cada dia: 01/01, 02/01 e 03/01.
     * 
     * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010,
     * e a periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma
     * a cada 7 dias: 01/01, 08/01, 15/01, 22/01 e 29/01.
     * 
     * Repare que a data da primeira sessao é sempre a data inicial.
     */
	public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
		if (inicio.isAfter(fim)) {
			throw new IllegalArgumentException("Data de inicio não pode ser maior do que a data fim");
		}

		List<Sessao> sessoes = new ArrayList<Sessao>();
		if (Periodicidade.DIARIA == periodicidade) {
			int dias = Days.daysBetween(inicio, fim).getDays();
			for (int i = 0; i <= dias; i++) {
				Sessao nova = new Sessao();
				nova.setEspetaculo(this);
				nova.setInicio(inicio.plusDays(i).toDateTime(horario));

				sessoes.add(nova);
			}
		} else {
			int semanas = Weeks.weeksBetween(inicio, fim).getWeeks();
			for (int i = 0; i <= semanas; i++) {
				Sessao nova = new Sessao();
				nova.setEspetaculo(this);
				nova.setInicio(inicio.plusWeeks(i).toDateTime(horario));

				sessoes.add(nova);
			}
		}

		return sessoes;
		/*List<Sessao> l = new ArrayList<Sessao>();
		if (periodicidade.equals(Periodicidade.DIARIA)) {
			//diaria
			Days d = Days.daysBetween(inicio, fim);
			int dias = d.getDays();
			
			if (dias >= 0 ) {
				
				for (int i = 0; i <= dias; i++) {
					Sessao s = new Sessao();
					
					s.setEspetaculo(this);
					s.setPreco(new BigDecimal("1.0"));
					s.setTotalIngressos(100);
					s.setIngressosReservados(1);
					s.setDuracaoEmMinutos(1);
					
					l.add(s);
				}
				
			} else {
				return l;
			}
			
		} else {
			//semanal
			Weeks.weeksBetween(inicio, fim);
			
		}
		
		
		return l;*/
	}
	
	public boolean Vagas(int qtd, int min)
   {
       // ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
       int totDisp = 0;

       for (Sessao s : sessoes)
       {
           if (s.getIngressosDisponiveis() < min) return false;
           totDisp += s.getIngressosDisponiveis();
       }

       if (totDisp >= qtd) return true;
       else return false;
   }

   public boolean Vagas(int qtd)
   {
       // ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
       int totDisp = 0;

       for (Sessao s : sessoes)
       {
           totDisp += s.getIngressosDisponiveis();
       }

       if (totDisp >= qtd) return true;
       else return false;
   }

}
