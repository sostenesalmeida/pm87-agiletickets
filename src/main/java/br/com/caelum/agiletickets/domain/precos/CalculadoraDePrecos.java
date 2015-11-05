package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	
	
	private static final double CINEMASHOW_VENDA = 0.05;
	private static final double CINEMASHOW_PRECO = 0.10;
	
	private static final double BALLET_ORQUESTRA_VENDA = 0.50;
	private static final double BALLET_ORQUESTRA_PRECO = 0.20;
	private static final int BALLET_ORQUESTRA_DURACAO = 60;
	private static final double BALLET_ORQUESTRA_DURACAO_PRECO = 0.10;
	

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		if(sessao.getTipoEspetaculo().equals(TipoDeEspetaculo.CINEMA) || sessao.getTipoEspetaculo().equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			preco = gerarPreco(sessao, CINEMASHOW_VENDA, CINEMASHOW_PRECO);
		} else if(sessao.getTipoEspetaculo().equals(TipoDeEspetaculo.BALLET) || sessao.getTipoEspetaculo().equals(TipoDeEspetaculo.ORQUESTRA) ) {
			preco = gerarPreco(sessao);
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	
	private static BigDecimal gerarPreco(Sessao sessao, Double perc, Double aliq) {
		BigDecimal preco;
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= perc) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(aliq)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}
	
	private static BigDecimal gerarPreco(Sessao sessao) {
		BigDecimal preco;
		preco = gerarPreco(sessao, BALLET_ORQUESTRA_VENDA, BALLET_ORQUESTRA_PRECO);
		
		if(sessao.getDuracaoEmMinutos() > BALLET_ORQUESTRA_DURACAO){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(BALLET_ORQUESTRA_DURACAO_PRECO)));
		}
		return preco;
	}



}