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

		final Categoria categoria1 = new Categoria(null, "Informática");
		final Categoria categoria2 = new Categoria(null, "Escritório");
		final Categoria categoria3 = new Categoria(null, "Cama Mesa e Banho");
		final Categoria categoria4 = new Categoria(null, "Eletronicos");
		final Categoria categoria5 = new Categoria(null, "Jardinagem");
		final Categoria categoria6 = new Categoria(null, "Decor");
		final Categoria categoria7 = new Categoria(null, "Perfumaria");

		final Produto produto1 = new Produto(null, "Computador", 2000.00);
		final Produto produto2 = new Produto(null, "Impressora", 800.00);
		final Produto produto3 = new Produto(null, "Mouse", 80.00);
		final Produto produto4 = new Produto(null, "Mesa de Escritorio", 300.00);
		final Produto produto5 = new Produto(null, "Toalha", 50.00);
		final Produto produto6 = new Produto(null, "Colcha", 200.00);
		final Produto produto7 = new Produto(null, "Tv True Color", 1200.00);
		final Produto produto8 = new Produto(null, "Rocadeira", 800.00);
		final Produto produto9 = new Produto(null, "Abajour", 100.00);
		final Produto produto10 = new Produto(null, "Pendente", 180.00);
		final Produto produto11 = new Produto(null, "Shampoo", 90.00);

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

		final ItemPedido itemPedidoOne = new ItemPedido(pedidoOne, produto1, 0.00, 1, 2000.00);
		final ItemPedido itemPedidoTwo = new ItemPedido(pedidoOne, produto3, 0.00, 2, 80.00);
		final ItemPedido itemPedidoThree = new ItemPedido(pedidoTwo, produto2, 100.00, 1, 800.00);

		pedidoOne.getItemsPedido().addAll(Arrays.asList(itemPedidoOne, itemPedidoTwo));
		pedidoTwo.getItemsPedido().addAll(Arrays.asList(itemPedidoThree));

		produto1.getItemsPedido().addAll(Arrays.asList(itemPedidoOne));
		produto2.getItemsPedido().addAll(Arrays.asList(itemPedidoThree));
		produto3.getItemsPedido().addAll(Arrays.asList(itemPedidoTwo));

		pedidoOne.setPagamento(pagamentoOne);
		pedidoTwo.setPagamento(pagamentoTwo);
		clienteOne.getPedidos().addAll(Arrays.asList(pedidoOne, pedidoTwo));

		clienteOne.getTelefones().addAll(Arrays.asList("30984021", "9918455655"));
		clienteOne.getEnderecos().addAll(Arrays.asList(enderecoOne, enderecoTwo));

		estadoOne.setCidades(Arrays.asList(cidadeOne));
		estadoTwo.setCidades(Arrays.asList(cidadeTwo, cidadeThree));

		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2, produto4));
		categoria3.getProdutos().addAll(Arrays.asList(produto5, produto6));
		categoria4.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3, produto7));
		categoria5.getProdutos().addAll(Arrays.asList(produto8));
		categoria6.getProdutos().addAll(Arrays.asList(produto9, produto10));
		categoria7.getProdutos().addAll(Arrays.asList(produto11));

		produto1.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2, categoria4));
		produto3.getCategorias().addAll(Arrays.asList(categoria1, categoria4));
		produto4.getCategorias().addAll(Arrays.asList(categoria2));
		produto5.getCategorias().addAll(Arrays.asList(categoria3));
		produto6.getCategorias().addAll(Arrays.asList(categoria3));
		produto7.getCategorias().addAll(Arrays.asList(categoria4));
		produto8.getCategorias().addAll(Arrays.asList(categoria5));
		produto9.getCategorias().addAll(Arrays.asList(categoria6));
		produto10.getCategorias().addAll(Arrays.asList(categoria6));
		produto11.getCategorias().addAll(Arrays.asList(categoria7));

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3, categoria4, categoria5, categoria6, categoria7));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4, produto5, produto6, produto7, produto8, produto9, produto10, produto11));
		estadoRepository.saveAll(Arrays.asList(estadoOne, estadoTwo));
		cidadeRepository.saveAll(Arrays.asList(cidadeOne, cidadeTwo, cidadeThree));
		clienteRepository.save(clienteOne);
		enderecoRepository.saveAll(Arrays.asList(enderecoOne, enderecoTwo));

		pedidoRepository.saveAll(Arrays.asList(pedidoOne, pedidoTwo));
		pagamentoRepository.saveAll(Arrays.asList(pagamentoOne, pagamentoTwo));
		itemPedidoRepository.saveAll(Arrays.asList(itemPedidoOne, itemPedidoTwo, itemPedidoThree));
	}

}
