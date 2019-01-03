package com.betancur.model;

import com.betancur.model.Empresa;
import com.betancur.model.EstadoCampo;
import com.betancur.model.Lote;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-30T21:30:09")
@StaticMetamodel(Campo.class)
public class Campo_ { 

    public static volatile SingularAttribute<Campo, Float> superficieCampo;
    public static volatile SingularAttribute<Campo, String> nombreCampo;
    public static volatile SingularAttribute<Campo, Long> id;
    public static volatile SingularAttribute<Campo, EstadoCampo> estadoCampo;
    public static volatile SingularAttribute<Campo, Empresa> empresa;
    public static volatile ListAttribute<Campo, Lote> listaDeLotes;

}