package com.betancur.model;

import com.betancur.model.Campo;
import com.betancur.model.TipoSuelo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-30T21:30:09")
@StaticMetamodel(Lote.class)
public class Lote_ { 

    public static volatile SingularAttribute<Lote, Integer> numeroLote;
    public static volatile SingularAttribute<Lote, Float> superficieLote;
    public static volatile SingularAttribute<Lote, Long> id;
    public static volatile SingularAttribute<Lote, TipoSuelo> tipoSuelo;
    public static volatile SingularAttribute<Lote, Campo> campo;

}