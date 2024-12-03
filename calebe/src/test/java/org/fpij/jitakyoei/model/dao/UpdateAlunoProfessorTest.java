package org.fpij.jitakyoei.model.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Endereco;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.util.DatabaseManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UpdateAlunoProfessorTest {
	
	private static DAO<Professor> professorDao;


	private static Aluno aluno;
	private static Entidade entidade;
	private static Endereco endereco;
	private static Filiado f1;
	private static Filiado filiadoProf;
	private static Professor professor;
	
	@BeforeClass
	public static void setUp(){
		DatabaseManager.setEnviroment(DatabaseManager.TEST);
		f1 = new Filiado();
		f1.setNome("Aécio");
		f1.setCpf("036.464.453-27");
		f1.setDataNascimento(new Date());
		f1.setDataCadastro(new Date());
		f1.setId(1332L);
		
		endereco = new Endereco();
		endereco.setBairro("Dirceu");
		endereco.setCep("64078-213");
		endereco.setCidade("Teresina");
		endereco.setEstado("PI");
		endereco.setRua("Rua Des. Berilo Mota");
		
		filiadoProf = new Filiado();
		filiadoProf.setNome("Professor");
		filiadoProf.setCpf("036.464.453-27");
		filiadoProf.setDataNascimento(new Date());
		filiadoProf.setDataCadastro(new Date());
		filiadoProf.setId(3332L);
		filiadoProf.setEndereco(endereco);
		
		professor = new Professor();
		professor.setFiliado(filiadoProf);
		
		entidade = new Entidade();
		entidade.setEndereco(endereco);
		entidade.setNome("Academia 1");
		entidade.setTelefone1("(086)1234-5432");
		
		aluno = new Aluno();
		aluno.setFiliado(f1);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		professorDao = new DAOImpl<Professor>(Professor.class);
	}

	public static void clearDatabase(){
		
		List<Professor> allP = professorDao.list();
		for (Professor each : allP) {
			professorDao.delete(each);
		}
		assertEquals(0, professorDao.list().size());
	}
	
	@Test
	public void testUpdateAluno() throws Exception {
		clearDatabase();

		Professor testProf = new Professor();

		Filiado profFiliado = new Filiado();
		profFiliado.setNome("Caio");
		profFiliado.setCpf("123.456.789-00");
		profFiliado.setDataNascimento(new Date());
		profFiliado.setDataCadastro(new Date());
		profFiliado.setId(3332L);
		profFiliado.setEndereco(endereco);

		testProf.setFiliado(profFiliado);

		professorDao.save(testProf);
		assertEquals(1, professorDao.list().size());
		assertEquals("123.456.789-00", professorDao.list().get(0).getFiliado().getCpf());
		assertEquals("Caio", professorDao.list().get(0).getFiliado().getNome());
	}
	
}
