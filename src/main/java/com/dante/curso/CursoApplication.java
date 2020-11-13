package com.dante.curso;

import com.dante.curso.domain.Categoria;
import com.dante.curso.domain.Cidade;
import com.dante.curso.domain.Cliente;
import com.dante.curso.domain.Endereco;
import com.dante.curso.domain.Estado;
import com.dante.curso.domain.ItemPedido;
import com.dante.curso.domain.Pagamento;
import com.dante.curso.domain.PagamentoBoleto;
import com.dante.curso.domain.PagamentoCartao;
import com.dante.curso.domain.Pedido;
import com.dante.curso.domain.Produto;
import com.dante.curso.domain.enums.EstadoPagamento;
import com.dante.curso.domain.enums.TipoCliente;
import com.dante.curso.repositories.CategoriaRepository;
import com.dante.curso.repositories.CidadeRepository;
import com.dante.curso.repositories.ClienteRepository;
import com.dante.curso.repositories.EnderecoRepository;
import com.dante.curso.repositories.EstadoRepository;
import com.dante.curso.repositories.ItemPedidoRepository;
import com.dante.curso.repositories.PagamentoRepository;
import com.dante.curso.repositories.PedidoRepository;
import com.dante.curso.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Override
	public void run(String... args) throws Exception {
		final Categoria categoriaOne = new Categoria(null, "Informática");
		final Categoria categoriaTwo = new Categoria(null, "Escritório");

		final Produto produtoOne = new Produto(null, "Computador", 2000.00);
		final Produto produtoTwo = new Produto(null, "Impressora", 800.00);
		final Produto produtoThree = new Produto(null, "Mouse", 80.00);

		final Estado estadoOne = new Estado(null, "Minas Gerais");
		final Estado estadoTwo = new Estado(null, "São Paulo");

		final Cidade cidadeOne = new Cidade(null, "Uberlância", estadoOne);
		final Cidade cidadeTwo = new Cidade(null, "São Paulo", estadoTwo);
		final Cidade cidadeThree = new Cidade(null, "Campinas", estadoTwo);

		final Cliente clienteOne = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOA_FISICA);
		final Endereco enderecoOne = new Endereco(null, "Rua Flores", "300", "30", "Jardim", "869120091", clienteOne, cidadeOne);
		final Endereco enderecoTwo = new Endereco(null, "Av Matos", "105", "Sala 800", "Centro", "783120065", clienteOne, cidadeTwo);

		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		final Pedido pedidoOne = new Pedido(null, sdf.parse("30/09/2020 10:32"), clienteOne, enderecoOne);
		final Pedido pedidoTwo = new Pedido(null, sdf.parse("10/10/2020 19:32"), clienteOne, enderecoTwo);

		final Pagamento pagamentoOne = new PagamentoCartao(null, EstadoPagamento.QUITADO, pedidoOne, 6);
		final Pagamento pagamentoTwo = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, pedidoTwo, sdf.parse("20/10/2020 00:00"), null);

		final ItemPedido itemPedidoOne = new ItemPedido(pedidoOne, produtoOne, 0.00, 1, 2000.00);
		final ItemPedido itemPedidoTwo = new ItemPedido(pedidoOne, produtoThree, 0.00, 2, 80.00);
		final ItemPedido itemPedidoThree = new ItemPedido(pedidoTwo, produtoTwo, 100.00, 1, 800.00);

		pedidoOne.getItemsPedido().addAll(Arrays.asList(itemPedidoOne, itemPedidoTwo));
		pedidoTwo.getItemsPedido().addAll(Arrays.asList(itemPedidoThree));

		produtoOne.getItemsPedido().addAll(Arrays.asList(itemPedidoOne));
		produtoTwo.getItemsPedido().addAll(Arrays.asList(itemPedidoThree));
		produtoThree.getItemsPedido().addAll(Arrays.asList(itemPedidoTwo));

		pedidoOne.setPagamento(pagamentoOne);
		pedidoTwo.setPagamento(pagamentoTwo);
		clienteOne.getPedidos().addAll(Arrays.asList(pedidoOne, pedidoTwo));

		clienteOne.getTelefones().addAll(Arrays.asList("30984021", "9918455655"));
		clienteOne.getEnderecos().addAll(Arrays.asList(enderecoOne, enderecoTwo));

		estadoOne.setCidades(Arrays.asList(cidadeOne));
		estadoTwo.setCidades(Arrays.asList(cidadeTwo, cidadeThree));

		categoriaOne.getProdutos().addAll(Arrays.asList(produtoOne, produtoTwo, produtoThree));
		categoriaTwo.getProdutos().addAll(Arrays.asList(produtoTwo));

		produtoOne.getCategorias().addAll(Arrays.asList(categoriaOne));
		produtoTwo.getCategorias().addAll(Arrays.asList(categoriaOne, categoriaTwo));
		produtoThree.getCategorias().addAll(Arrays.asList(categoriaOne));

		categoriaRepository.saveAll(Arrays.asList(categoriaOne, categoriaTwo));
		produtoRepository.saveAll(Arrays.asList(produtoOne, produtoTwo, produtoThree));
		estadoRepository.saveAll(Arrays.asList(estadoOne, estadoTwo));
		cidadeRepository.saveAll(Arrays.asList(cidadeOne, cidadeTwo, cidadeThree));
		clienteRepository.save(clienteOne);
		enderecoRepository.saveAll(Arrays.asList(enderecoOne, enderecoTwo));

		pedidoRepository.saveAll(Arrays.asList(pedidoOne, pedidoTwo));
		pagamentoRepository.saveAll(Arrays.asList(pagamentoOne, pagamentoTwo));
		itemPedidoRepository.saveAll(Arrays.asList(itemPedidoOne, itemPedidoTwo, itemPedidoThree));
	}

}
