/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author RenatoCampos
 */
public class ProjectController {
    
    public void saveProject(Project project){
        String sql = "INSERT INTO projects (name, description"
                + ",createdAt, updatedAt)"
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto "
                    + ex.getMessage(), ex);
        }finally{
            //AULA 11K
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void updateProject(Project project){
        
        String sql = "UPLOAD projects SET "
                + "idProject=?,"
                + "name=?"
                + "description=?"
                + "createdAt=?"
                + "updatedAt=?)"
                + "WHERE id=?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //CRIANDO CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PREPARANDO O STATEMENT
            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES DO STATEMENT
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (SQLException ex) {
            //TRATANDO UM POSSÍVEL ERRO
            throw new RuntimeException("Erro ao atualizar o projeto "
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeProject(){
        String sql = "DELETE FROM projects WHERE id=?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareCall(sql);
            statement.setInt(1,0); //setar o ID no lugar do ZERO
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o projeto "
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> getAllProjects(){
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projetos = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        //Instanciar um ResultSet para receber o Select do banco de dados
        ResultSet resultSet = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareCall(sql);
            
            resultSet = statement.executeQuery();
            
            //ENQUANTO EXISTIR DADOS NO BANCO DE DADOS, FAÇA
            
            while(resultSet.next()){
                
                Project proj = new Project();
                
                proj.setId(resultSet.getInt("id"));
                proj.setName(resultSet.getString("name"));
                proj.setDescription(resultSet.getString("description"));
                proj.setCreatedAt(resultSet.getDate("createdAt"));
                proj.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                projetos.add(proj);
            }
            
            
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar os projetos", ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        return projetos;
    }
}
