package com.bemedica.springboot.app.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="codigo_confirmacion")
public class CodigoConfirmacion 
{
	@Id
	@Column(name="id_cod_con")
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native",strategy="native")
	private int id_cod_con;
	
	@Column(name="codigo")
	private String codigo;
	
	
	@Column(name="usermed")
	private int usermed;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public int getusermed() {
		return usermed;
	}

	public void setusermed(int usermed) {
		this.usermed = usermed;
	}
}
