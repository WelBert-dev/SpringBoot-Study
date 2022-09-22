# SpringBoot-Study

1. Criando um projeto do zero sem o SpringBoot em sí
   1. File -> New -> Project -> Name -> Directory
   2. Estrutura de pastas padrão do mavem: src/main+test
   3. arquivo de configuração do mavem: pom.xml

2. As Dependências do Maven ficam no diretorio home: ~/.m2
   1. tags parent e dependencies são as definicios dos modulos em pom.xml 
   2. Para vizualizar o pom final com todas as dependências:
    1. Botão direito sobre o pom -> Maven -> Show Effective POM

3. Após as configurações iniciais setadas em pom.xml, deve-se criar o 
   primeiro pacote em ./src/main/java/ <- "academy.devdojo.springboot2"
   1. Create the sub-directory "domain"
    1. Create the Anime class in this 
   2. Create the sub-directory "start"
    1. Cria a classe "ApplicationStart" -> main
        1. psvm -> ponto inicial de start
            1. SpringApplication.run(ApplicationStart.class, args);
    2. Ao executar ira ocorrer alguns problemas, pois é preciso setar algumas
           configurações
        1. Primeira solução: utilizar a anotação @EnableAutoConfiguration

4. Criando o primeiro EndPoint basico sem boas práticas: nova pasta em academy.devdojo.springboot2/controller
   1. Cria a classe "AnimeController" in this com a anotação @RestController
        1. Esta anotação irá tornar a classe um bean spring mais especifico com outros sub-beans sendo eles:
           1. @Target({ElementType.TYPE})
           2. @Retention(RetentionPolicy.RUNTIME)
           3. @Documented: 
           4. @Controller: Anotação que vem do spring MVC indicando que é um controller.
           5. @ResponseBody: Indica que o retorno do método é String ou seja Json (Não retorna uma View por exe.)
        2. Nesta classe cria o método inicial list que sera responsável por retornar uma lista de Animes.
           1. return List.of(new Anime("DBZ"), new Anime("Hellsing"));
           2. Não ira retornar ainda pois é necessário indicar qual será o método e os parametros HTTP:
              1. Antigamente era utilizado a anotação @RequestMapping(method = RequestMethod.GET, path = "list")
                 1. Em nível de método define qual endpoint irá ser acessado, que neste caso não existe contexto é
                    1. localhost:8080/list
                 2. Em nível de classe a anotação  @RequestMapping("anime") adiciona um contexto ao controller 
                    1. agora iremos acessar o mesmo Endpoint anterior porém agr adicionando o contexto
                       1. localhost:8080/anime/list 
              2. Porém o RequestMapping a nível de método anterior esta obsoleto, nova anotação com mesmo result é:
                 1.  @GetMapping(path = "list") <=> @RequestMapping(method = RequestMethod.GET, path = "list")
   2. Após criado iremos rodar, porém ira ocorrer um error 404 pois o ApplicationStart está em um caminho inválido!
      1. O spring busca na raiz do pacote pelo ApplicationStart sendo no path "academy.devdojo.springboot2"
         e o start se encontra no path "academy.devdojo.springboot2.start.ApplicationStart"
      2. Possiveis correções:
         1. Utilizar a anotação @ComponentScan(basePackages = "academy.devdojo.springboot2")
            1. Poŕem isto não é uma boa prática, pois ira "forçar" o inicializador buscar por ele neste path!
               1. Funcionará porém ira dar outro erro agora com status 500, isto pois o "jackson"
                  Que é o serializador do spring, ira buscar pr um getter e setter da classe Anime para poder gerar
                  O Json, porém nós não implementamos, apenas implementamos o contrutor!
                  1. Solução: Gerar os Getter e Setter da classe de dominio "Anime" em "academy.devdojo.springboot2.domain.Anime"
                     1. Pronto! agora não ira dar mais erros!
         2. No proximos passos iremos Utilizar a maneira CORRETA! -> Step 6

5. @Autowired e @Component -> Video 04 DevDojo
   1. Cria o pacote "util" na raiz "academy.devdojo.springboot2" <- aqui
      1. Cria a classe "DateUtil" in this
         1. Cria método que retorna a data formatada no padrão db in this
   2. Cria a propriedade "dateUtil" em "academy.devdojo.springboot2.controller.AnimeController.java"
      1. E faz a injeção de dependência utilizando:
         1. @Autowired -> Não recomendado popis dificulta cenários de teste:
            1. De primeira ocorrerá um erro pois não utilizamos nenhuma anotação para torna-lo um bean!
               1. Para isto iremos editar a classe "dateUtil" criada anteriormente em "...springboot2.util.DateUtil.java"
                  1. Utilizando a anotação @Component na classe (Pois não é uma classe com especialidades tipo controller, service e etc...)
                  2. Obs: Isto só é possível pois a classe "main" ApplicationStart contém a anotação @ComponentScan
                     No qual esta realizando o mapeamento de todos pacotes em "academy.devdojo.springboot2"
                     Porisso é possível utilizar as anotações e o spring encontra!
               
         2. Utilizando os atributos set da classe:
         
         3. Pelo construtor da classe -> Mais recomendado

6. Corrigindo o arquivo "main" ApplicationStart em "academy.devdojo.springboot2.start.ApplicationStart.java"
   Correção esta pois o "correto" é deixar este ponto de partida no root do pacote ou seja em "academy.devdojo.springboot2"
   Estava funcionando pois "forçamos" utilizando a anotação @ComponentScan(basePackages = "academy.devdojo.springboot2")
   Porém não é uma boa prática!
   1. Inicialmente iremos mover a classe java para a raiz do pacote "academy.devdojo.springboot2" <- aqui
   2. Remover o pacote "start" em "academy.devdojo.springboot2.start"
   3. Remover o parametro da anotação anterior @ComponentScan -> assim ele scanneia por padrão a pasta corrente
   4. Parei em 5:30
      