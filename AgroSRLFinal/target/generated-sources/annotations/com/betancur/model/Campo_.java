package com.betancur.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Campo.class)
public abstract class Campo_ {

	public static volatile SingularAttribute<Campo, Float> superficieCampo;
	public static volatile SingularAttribute<Campo, String> nombreCampo;
	public static volatile SingularAttribute<Campo, Empresa> unaEmpresa;
	public static volatile SingularAttribute<Campo, Long> id;
	public static volatile SingularAttribute<Campo, EstadoCampo> unEstadoCampo;
	public static volatile ListAttribute<Campo, Lote> listaLotes;

}

