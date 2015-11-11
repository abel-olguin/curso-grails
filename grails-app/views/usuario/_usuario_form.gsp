<f:field property="nombre" bean="usuario" class="nombre-inp"/>

<f:field property="primerApellido" bean="usuario"/>

<f:field property="segundoApellido" bean="usuario"/>

<f:field property="edad" bean="usuario">
    <g:select name="edad" from="${18..65}" noSelection="['':'-Elige tu edad-']" value="${usuario?.edad}"/>
</f:field>

<f:field property="sexo" bean="usuario"/>

<f:field property="email" bean="usuario"/>

<f:field property="casado" bean="usuario"/>