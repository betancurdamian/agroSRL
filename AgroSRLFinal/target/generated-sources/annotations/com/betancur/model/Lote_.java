package com.betancur.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lote.class)
public abstract class Lote_ {

	public static volatile SingularAttribute<Lote, Integer> numeroLote;
	public static volatile SingularAttribute<Lote, Float> superficieLote;
	public static volatile SingularAttribute<Lote, TipoSuelo> unTipoSuelo;
	public static volatile SingularAttribute<Lote, Campo> unCampo;
	public static volatile SingularAttribute<Lote, Long> id;

}

