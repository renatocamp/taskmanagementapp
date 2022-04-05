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
import model.Tasks;
import util.ConnectionFactory;

/**
 *
 * @author RenatoCampos
 */
public class TaskController {
    
    public void save(Tasks task){
        
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1,task.getIdProject());
            statement.setString(2,task.getName());
            statement.setString(3,task.getDescription());
            statement.setBoolean(4,task.isIsCompleted());
            statement.setString(5,task.getNotes());
            statement.setDate(6, new Date(task.getCreatedAt().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getCreatedAt().getTime()));
            statement.execute();
        }catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Tasks task){
        String sql = "UPDATE tasks SET (idProject =?,"
                + "name =?,"
                + "description =?,"
                + "completed =?,"
                + "notes =?,"
                + "deadline =?,"
                + "createdAt =?,"
                + "updatedAt =?,"
                + "WHERE id=?;";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //ESTABELECENDO A CONEXÃO COM O B.D
            connection = ConnectionFactory.getConnection();
            //PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES NO STATEMENT
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa"
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void removeById(int taskId) throws SQLException{
        String sql = "DELETE FROM tasks WHERE id= ?";
        Connection connection = null;
        PreparedStatement statetment = null;
        
        try {
            //CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            //PRAPARANDO A QUERY
            statetment = connection.prepareStatement(sql);
            //SETANDO O VALOR A SER DELETADO
            statetment.setInt(1, taskId);
            statetment.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa"
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statetment);
        }
        
    }
    
    public List<Tasks> getAll(int idProject){
        String sql = "SELECT * FROM tasks WHERE idProject =? ";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Tasks> tasks = new ArrayList<>();
        
        try {
            
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Tasks task = new Tasks();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                tasks.add(task);
            }
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir as tarefas"
                    + ex.getMessage(), ex);
        }finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        //LISTA DE TAREFAS CRIADAS E CARREGADAS PELO BANCO DE DADOS
        return tasks;
    }
}
