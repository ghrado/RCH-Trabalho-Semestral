# Arquitetura do Sistema - Documentação Completa

## Índice
1. [Visão Geral](#visão-geral)
2. [Estrutura de Camadas](#estrutura-de-camadas)
3. [Modelo de Dados](#modelo-de-dados)
4. [Camada de Serviços](#camada-de-serviços)
5. [Camada de Visualização](#camada-de-visualização)
6. [Fluxos de Navegação](#fluxos-de-navegação)
7. [Segurança e Auditoria](#segurança-e-auditoria)
8. [Persistência de Dados](#persistência-de-dados)
9. [Padrões de Design](#padrões-de-design)
10. [Tratamento de Erros](#tratamento-de-erros)

---

## Visão Geral

Sistema de gestão empresarial desenvolvido em Java Swing seguindo arquitetura em camadas baseada no padrão MVC (Model-View-Controller) com camada de serviços adicional. O sistema gerencia vendas, estoque, caixa, clientes e usuários com controle de acesso baseado em perfis.

### Tecnologias Utilizadas
- **Linguagem**: Java 8+
- **Interface Gráfica**: Java Swing
- **Banco de Dados**: Sistema de arquivos (serialização)
- **Geração de PDF**: iText ou similar
- **Padrão Arquitetural**: MVC + Service Layer

---

## Estrutura de Camadas

\`\`\`
┌─────────────────────────────────────────┐
│         Camada de Apresentação          │
│              (View/Telas)               │
├─────────────────────────────────────────┤
│         Camada de Controle              │
│          (Controllers/Services)         │
├─────────────────────────────────────────┤
│         Camada de Negócio               │
│         (Model + Interfaces)            │
├─────────────────────────────────────────┤
│         Camada de Utilitários           │
│    (Database, Auth, Validation, etc)    │
└─────────────────────────────────────────┘
\`\`\`

---

## Modelo de Dados

### Hierarquia de Classes Base

#### UniversalObject (Abstrata)
Classe base para todas as entidades do sistema.

**Atributos:**
- `String id`: Identificador único
- `LocalDateTime dataCriacao`: Data de criação do registro
- `LocalDateTime dataModificacao`: Data da última modificação
- `boolean ativo`: Flag para soft delete

**Métodos Abstratos:**
- `String toString()`
- `boolean equals(Object obj)`
- `int hashCode()`

**Métodos Concretos:**
- `void marcarComoInativo()`: Soft delete
- `void atualizarDataModificacao()`: Atualiza timestamp

---

### Hierarquia de Usuários

#### Usuario (Abstrata extends UniversalObject)
Classe base para todos os tipos de usuários do sistema.

**Atributos:**
- `String nome`: Nome completo
- `String username`: Nome de usuário único
- `String senha`: Senha criptografada
- `String email`: Email do usuário
- `String telefone`: Telefone de contato
- `boolean bloqueado`: Status de bloqueio
- `int tentativasLogin`: Contador de tentativas falhas

**Implementa:** `Autenticavel`

**Métodos:**
- `boolean autenticar(String senha)`: Valida credenciais
- `void bloquearConta()`: Bloqueia após tentativas falhas
- `void desbloquearConta()`: Desbloqueia conta
- `void resetarTentativas()`: Reseta contador

#### Subclasses de Usuario

**Administrador**
- Acesso total ao sistema
- Gerencia todos os usuários
- Acesso a relatórios consolidados
- Controle de fechamento de caixa

**Gerente**
- Gerencia vendas e estoque
- Visualiza relatórios de vendas
- Gerencia vendedores
- Controle de produtos

**Vendedor**
- Realiza vendas
- Gerencia clientes
- Cria ordens de venda
- Visualiza próprias vendas

**Caixa**
- Processa pagamentos
- Lança recebimentos
- Visualiza ordens pendentes
- Fecha caixa diário

---

### Hierarquia de Clientes

#### Cliente (Abstrata extends UniversalObject)
Classe base para clientes do sistema.

**Atributos Comuns:**
- `String nome`: Nome/Razão social
- `String endereco`: Endereço completo
- `String telefone`: Telefone principal
- `String email`: Email de contato
- `List<Ordem> historicoCompras`: Histórico de compras

**Métodos:**
- `abstract String getDocumento()`: Retorna CPF ou NUIT
- `double calcularTotalCompras()`: Total histórico
- `List<Ordem> getComprasRecentes(int dias)`: Compras recentes

#### ClienteSingular (extends Cliente)
Cliente pessoa física.

**Atributos Específicos:**
- `String cpf`: CPF do cliente
- `LocalDate dataNascimento`: Data de nascimento

#### ClienteEmpresa (extends Cliente)
Cliente pessoa jurídica.

**Atributos Específicos:**
- `String nuit`: NUIT da empresa
- `String nomeFantasia`: Nome fantasia
- `String inscricaoEstadual`: Inscrição estadual

---

### Entidades de Negócio

#### Produto (extends UniversalObject)
Representa produtos em estoque.

**Atributos:**
- `String codigo`: Código único do produto
- `String nome`: Nome do produto
- `String descricao`: Descrição detalhada
- `String categoria`: Categoria do produto
- `double preco`: Preço unitário
- `int quantidadeEstoque`: Quantidade disponível
- `int estoqueMinimo`: Estoque mínimo
- `String unidadeMedida`: Unidade (UN, KG, L, etc)

**Métodos:**
- `boolean temEstoque(int quantidade)`: Verifica disponibilidade
- `void adicionarEstoque(int quantidade)`: Adiciona ao estoque
- `void removerEstoque(int quantidade)`: Remove do estoque
- `boolean estoqueAbaixoMinimo()`: Verifica se está abaixo do mínimo

#### Ordem (extends UniversalObject)
Representa pedidos de venda.

**Atributos:**
- `String numeroOrdem`: Número único da ordem
- `Cliente cliente`: Cliente da ordem
- `Usuario vendedor`: Vendedor responsável
- `List<ItemOrdem> itens`: Itens da ordem
- `LocalDateTime dataOrdem`: Data/hora da ordem
- `String status`: Status (PENDENTE, PAGO, CANCELADO)
- `double valorTotal`: Valor total
- `double desconto`: Desconto aplicado
- `String observacoes`: Observações

**Métodos:**
- `void adicionarItem(ItemOrdem item)`: Adiciona item
- `void removerItem(ItemOrdem item)`: Remove item
- `double calcularTotal()`: Calcula total
- `void aplicarDesconto(double percentual)`: Aplica desconto
- `void marcarComoPago()`: Marca como pago
- `void cancelar()`: Cancela ordem

#### ItemOrdem (extends UniversalObject)
Itens individuais de uma ordem.

**Atributos:**
- `Produto produto`: Produto do item
- `int quantidade`: Quantidade
- `double precoUnitario`: Preço no momento da venda
- `double subtotal`: Subtotal do item

**Métodos:**
- `double calcularSubtotal()`: Calcula subtotal
- `void atualizarQuantidade(int novaQuantidade)`: Atualiza quantidade

#### Pagamento (extends UniversalObject)
Registros de pagamentos.

**Atributos:**
- `Ordem ordem`: Ordem relacionada
- `String tipoPagamento`: Tipo (DINHEIRO, CARTAO, TRANSFERENCIA, etc)
- `double valor`: Valor pago
- `LocalDateTime dataPagamento`: Data/hora do pagamento
- `Usuario caixa`: Caixa que processou
- `String numeroTransacao`: Número da transação
- `String status`: Status do pagamento

**Métodos:**
- `void processar()`: Processa pagamento
- `void estornar()`: Estorna pagamento
- `boolean isAprovado()`: Verifica se aprovado

#### Fatura (extends UniversalObject)
Documentos fiscais.

**Atributos:**
- `String numeroFatura`: Número único da fatura
- `Ordem ordem`: Ordem relacionada
- `Cliente cliente`: Cliente da fatura
- `LocalDateTime dataEmissao`: Data de emissão
- `double valorTotal`: Valor total
- `double impostos`: Impostos
- `String observacoes`: Observações

**Métodos:**
- `void gerar()`: Gera fatura
- `byte[] gerarPDF()`: Gera PDF da fatura
- `void enviarPorEmail()`: Envia por email

#### Caixa (extends UniversalObject)
Controle de caixa.

**Atributos:**
- `LocalDate data`: Data do caixa
- `Usuario responsavel`: Responsável pelo caixa
- `double saldoInicial`: Saldo inicial
- `double saldoFinal`: Saldo final
- `List<Pagamento> pagamentos`: Pagamentos do dia
- `boolean fechado`: Status de fechamento
- `LocalDateTime dataFechamento`: Data/hora do fechamento

**Métodos:**
- `void adicionarPagamento(Pagamento pagamento)`: Adiciona pagamento
- `double calcularTotal()`: Calcula total
- `Map<String, Double> getTotaisPorTipo()`: Totais por tipo de pagamento
- `void fechar()`: Fecha o caixa

#### AuditLog (extends UniversalObject)
Logs de auditoria do sistema.

**Atributos:**
- `Usuario usuario`: Usuário que executou a ação
- `String acao`: Descrição da ação
- `String entidade`: Entidade afetada
- `String entidadeId`: ID da entidade
- `LocalDateTime dataHora`: Data/hora da ação
- `String detalhes`: Detalhes adicionais
- `String ipAddress`: Endereço IP

---

## Camada de Serviços

### Interfaces de Serviço

#### Gerenciavel<T>
Interface genérica para operações CRUD.

**Métodos:**
- `void adicionar(T entidade)`: Adiciona nova entidade
- `void atualizar(T entidade)`: Atualiza entidade existente
- `void remover(String id)`: Remove entidade (soft delete)
- `T buscarPorId(String id)`: Busca por ID
- `List<T> listarTodos()`: Lista todas as entidades
- `List<T> buscar(Predicate<T> criterio)`: Busca com critério

#### Autenticavel
Interface para autenticação.

**Métodos:**
- `boolean autenticar(String username, String senha)`: Autentica usuário
- `void logout()`: Encerra sessão
- `Usuario getUsuarioLogado()`: Retorna usuário logado

#### Relatorio<T>
Interface para geração de relatórios.

**Métodos:**
- `List<T> gerarRelatorio(LocalDate inicio, LocalDate fim)`: Gera relatório
- `byte[] exportarPDF(List<T> dados)`: Exporta para PDF
- `Map<String, Object> gerarEstatisticas(List<T> dados)`: Gera estatísticas

---

### Implementações de Serviços

#### ControleGestorService
Gerencia usuários do sistema.

**Responsabilidades:**
- CRUD de usuários (Administrador, Gerente, Vendedor, Caixa)
- Autenticação e controle de sessão
- Bloqueio/desbloqueio de contas
- Validação de permissões

**Métodos Principais:**
- `Usuario autenticar(String username, String senha)`
- `void criarUsuario(Usuario usuario)`
- `void alterarSenha(String userId, String novaSenha)`
- `List<Usuario> listarPorTipo(Class<? extends Usuario> tipo)`
- `void bloquearUsuario(String userId)`

#### VendasService
Gerencia clientes e ordens de venda.

**Responsabilidades:**
- CRUD de clientes
- CRUD de ordens de venda
- Cálculo de totais e descontos
- Geração de faturas
- Relatórios de vendas

**Métodos Principais:**
- `void criarCliente(Cliente cliente)`
- `Ordem criarOrdem(Cliente cliente, Usuario vendedor)`
- `void adicionarItemOrdem(Ordem ordem, Produto produto, int quantidade)`
- `void finalizarOrdem(Ordem ordem)`
- `List<Ordem> buscarOrdensPendentes()`
- `List<Ordem> buscarOrdensPorCliente(String clienteId)`
- `double calcularTotalVendas(LocalDate inicio, LocalDate fim)`

#### StockService
Gerencia produtos e estoque.

**Responsabilidades:**
- CRUD de produtos
- Controle de estoque
- Alertas de estoque baixo
- Movimentações de estoque
- Relatórios de estoque

**Métodos Principais:**
- `void adicionarProduto(Produto produto)`
- `void atualizarEstoque(String produtoId, int quantidade)`
- `List<Produto> listarProdutosBaixoEstoque()`
- `boolean verificarDisponibilidade(String produtoId, int quantidade)`
- `void registrarMovimentacao(String produtoId, int quantidade, String tipo)`

#### CaixaService
Gerencia pagamentos e fechamento de caixa.

**Responsabilidades:**
- Processamento de pagamentos
- Controle de caixa diário
- Fechamento de caixa
- Relatórios financeiros

**Métodos Principais:**
- `void processarPagamento(Ordem ordem, String tipoPagamento, double valor)`
- `Caixa abrirCaixa(Usuario responsavel, double saldoInicial)`
- `void fecharCaixa(String caixaId)`
- `Map<String, Double> gerarResumoFechoCaixa(String caixaId)`
- `List<Pagamento> listarPagamentosDia(LocalDate data)`

#### RelatoriosService
Gera relatórios diversos.

**Responsabilidades:**
- Relatórios de vendas (diário, semanal, mensal)
- Relatórios de estoque
- Relatórios financeiros
- Exportação para PDF

**Métodos Principais:**
- `List<Ordem> gerarRelatorioDiario(LocalDate data)`
- `List<Ordem> gerarRelatorioSemanal(LocalDate inicio)`
- `List<Ordem> gerarRelatorioMensal(int mes, int ano)`
- `Map<String, Object> gerarRelatorioConsolidado(LocalDate inicio, LocalDate fim)`
- `byte[] exportarRelatorioPDF(List<Ordem> ordens)`

---

## Camada de Visualização

### Estrutura de Telas

#### Telas de Autenticação

**Login**
- Autenticação de usuários
- Validação de credenciais
- Redirecionamento baseado em perfil
- Recuperação de senha

#### Telas Dashboard (por perfil)

**TelaAdministrador**
- Acesso a todas as funcionalidades
- Botões: Controle de Usuários, Relatórios, Estoque, Vendas, Fechamento de Caixa
- Navegação para:
  - TelaControleOpcoes
  - TelaRelatorios
  - TelaStockOpcoes
  - TelaVendas
  - TelaFechoCaixa

**TelaGerente**
- Gerenciamento de vendas e estoque
- Botões: Vendas, Estoque, Relatórios
- Navegação para:
  - TelaVendas
  - TelaStockOpcoes
  - TelaRelatorios

**TelaVendedor**
- Realização de vendas
- Botões: Vendas, Clientes
- Navegação para:
  - TelaVendas
  - TelaClientes

**TelaCaixa**
- Processamento de pagamentos
- Botões: Caixa, Pagamentos
- Navegação para:
  - TelaCaixaOpcoes
  - TelaOrdemPagamento

---

### Módulos Funcionais

#### Módulo de Controle de Usuários

**TelaControleOpcoes**
- Lista usuários por tipo
- Botões: Adicionar, Editar, Remover, Bloquear/Desbloquear
- Navegação para TelaGerenciarUsuario

**TelaGerenciarUsuario**
- Formulário de cadastro/edição
- Campos: Nome, Username, Email, Telefone, Tipo, Senha
- Validações de campos obrigatórios
- Botão Voltar para TelaControleOpcoes

#### Módulo de Vendas

**TelaVendas**
- Lista de ordens de venda
- Filtros: Status, Data, Cliente
- Botões: Nova Ordem, Clientes, Visualizar
- Navegação para:
  - TelaOrdem (criar/editar)
  - TelaClientes

**TelaClientes**
- Lista de clientes
- Filtros: Tipo, Nome
- Botões: Adicionar, Editar, Remover
- Formulário inline para cadastro rápido
- Botão Voltar para TelaVendas

**TelaOrdem**
- Formulário de ordem de venda
- Seleção de cliente
- Tabela de itens
- Botões: Adicionar Item, Remover Item, Aplicar Desconto, Finalizar
- Cálculo automático de totais
- Botão Voltar para TelaVendas

#### Módulo de Estoque

**TelaStockOpcoes**
- Lista de produtos
- Filtros: Categoria, Estoque Baixo
- Botões: Adicionar, Editar, Remover, Ajustar Estoque
- Alertas de estoque baixo
- Botão Voltar para dashboard

#### Módulo de Caixa

**TelaCaixaOpcoes**
- Status do caixa (aberto/fechado)
- Botões: Abrir Caixa, Fechar Caixa, Lançamentos, Ordens Pendentes
- Navegação para:
  - TelaOrdemPagamento
  - TelaLancamentoPagamento
  - TelaFechoCaixa

**TelaOrdemPagamento**
- Lista de ordens pendentes de pagamento
- Seleção de ordem
- Botão: Processar Pagamento
- Navegação para TelaLancamentoPagamento

**TelaLancamentoPagamento**
- Formulário de pagamento
- Campos: Tipo de Pagamento, Valor, Observações
- Cálculo de troco (para dinheiro)
- Botões: Confirmar, Cancelar
- Botão Voltar para TelaOrdemPagamento

**TelaFechoCaixa**
- Resumo do caixa do dia
- Totais por tipo de pagamento
- Saldo inicial e final
- Botão: Fechar Caixa
- Geração de relatório PDF
- Botão Voltar para TelaAdministrador

#### Módulo de Relatórios

**TelaRelatorios**
- Botões para tipos de relatório:
  - Relatório Diário
  - Relatório Semanal
  - Relatório Mensal
  - Relatório Consolidado
- Botão Voltar para dashboard

**RelatoriosDialog**
- Dialog modal para seleção de período
- Campos: Data Início, Data Fim
- Botões: Gerar, Exportar PDF, Cancelar
- Exibição de resultados em tabela
- Estatísticas resumidas

---

## Fluxos de Navegação

### Fluxo de Autenticação

\`\`\`
Login
  ↓ (autenticar)
AuthenticationManager.autenticar()
  ↓ (validar credenciais)
ControleGestorService.autenticar()
  ↓ (verificar tipo de usuário)
Redirecionar para dashboard específico:
  - Administrador → TelaAdministrador
  - Gerente → TelaGerente
  - Vendedor → TelaVendedor
  - Caixa → TelaCaixa
\`\`\`

### Fluxo de Venda Completo

\`\`\`
TelaVendas
  ↓ (Nova Ordem)
TelaOrdem
  ↓ (Selecionar Cliente)
TelaClientes (se necessário criar novo)
  ↓ (Voltar)
TelaOrdem
  ↓ (Adicionar Itens)
StockService.verificarDisponibilidade()
  ↓ (Finalizar Ordem)
VendasService.finalizarOrdem()
  ↓ (Gerar Fatura)
Fatura.gerar()
  ↓ (Voltar)
TelaVendas
\`\`\`

### Fluxo de Pagamento

\`\`\`
TelaCaixaOpcoes
  ↓ (Ordens Pendentes)
TelaOrdemPagamento
  ↓ (Selecionar Ordem)
TelaLancamentoPagamento
  ↓ (Confirmar Pagamento)
CaixaService.processarPagamento()
  ↓ (Atualizar Status Ordem)
VendasService.marcarComoPago()
  ↓ (Registrar em Caixa)
Caixa.adicionarPagamento()
  ↓ (Voltar)
TelaOrdemPagamento
\`\`\`

### Fluxo de Fechamento de Caixa

\`\`\`
TelaAdministrador
  ↓ (Fechamento de Caixa)
TelaFechoCaixa
  ↓ (Carregar Dados)
CaixaService.gerarResumoFechoCaixa()
  ↓ (Exibir Resumo)
Totais por tipo de pagamento
  ↓ (Fechar Caixa)
CaixaService.fecharCaixa()
  ↓ (Gerar PDF)
PDFGenerator.gerarRelatorioCaixa()
  ↓ (Registrar Auditoria)
AuditService.registrar()
  ↓ (Voltar)
TelaAdministrador
\`\`\`

---

## Segurança e Auditoria

### Autenticação

**AuthenticationManager**
- Gerencia sessões de usuários
- Valida credenciais
- Controla tentativas de login
- Bloqueia contas após 3 tentativas falhas
- Mantém usuário logado em memória

**Processo de Autenticação:**
1. Usuário insere username e senha
2. AuthenticationManager valida formato
3. ControleGestorService busca usuário
4. Verifica se conta está bloqueada
5. Valida senha (comparação com hash)
6. Incrementa tentativas se falhar
7. Bloqueia após 3 tentativas
8. Registra login em AuditLog
9. Retorna usuário autenticado

### Controle de Acesso

**Baseado em Perfil:**
- Cada tela verifica tipo de usuário
- Botões habilitados/desabilitados conforme perfil
- Navegação restrita por tipo de usuário

**Matriz de Permissões:**

| Funcionalidade | Admin | Gerente | Vendedor | Caixa |
|----------------|-------|---------|----------|-------|
| Gerenciar Usuários | ✓ | ✗ | ✗ | ✗ |
| Vendas | ✓ | ✓ | ✓ | ✗ |
| Estoque | ✓ | ✓ | ✗ | ✗ |
| Pagamentos | ✓ | ✗ | ✗ | ✓ |
| Fechamento Caixa | ✓ | ✗ | ✗ | ✗ |
| Relatórios Completos | ✓ | ✓ | ✗ | ✗ |
| Relatórios Próprios | ✓ | ✓ | ✓ | ✓ |

### Auditoria

**AuditService**
Registra todas as ações críticas do sistema.

**Ações Auditadas:**
- Login/Logout de usuários
- Criação/Edição/Remoção de entidades
- Processamento de pagamentos
- Fechamento de caixa
- Alterações de estoque
- Geração de relatórios

**Informações Registradas:**
- Usuário que executou
- Data/hora da ação
- Tipo de ação
- Entidade afetada
- Detalhes da operação
- Endereço IP (se disponível)

**Exemplo de Registro:**
\`\`\`java
AuditLog log = new AuditLog();
log.setUsuario(usuarioLogado);
log.setAcao("CRIAR_ORDEM");
log.setEntidade("Ordem");
log.setEntidadeId(ordem.getId());
log.setDetalhes("Ordem criada para cliente: " + cliente.getNome());
log.setDataHora(LocalDateTime.now());
auditService.registrar(log);
\`\`\`

---

## Persistência de Dados

### DatabaseConnection

**Responsabilidades:**
- Gerenciar conexão com banco de dados
- Serialização/Deserialização de objetos
- Transações
- Backup e recuperação

**Estratégia de Persistência:**
- Serialização de objetos Java
- Arquivos separados por entidade
- Índices para busca rápida
- Backup automático periódico

**Estrutura de Arquivos:**
\`\`\`
data/
  ├── usuarios.dat
  ├── clientes.dat
  ├── produtos.dat
  ├── ordens.dat
  ├── pagamentos.dat
  ├── caixas.dat
  ├── faturas.dat
  └── audit_logs.dat
\`\`\`

**Operações:**
- `salvar(Object entidade)`: Salva entidade
- `buscar(String id, Class<T> tipo)`: Busca por ID
- `listar(Class<T> tipo)`: Lista todas
- `atualizar(Object entidade)`: Atualiza entidade
- `remover(String id, Class<T> tipo)`: Remove entidade
- `backup()`: Cria backup
- `restaurar(String backupPath)`: Restaura backup

---

## Padrões de Design

### 1. MVC (Model-View-Controller)
- **Model**: Entidades de negócio (Model/)
- **View**: Telas Swing (Telas/)
- **Controller**: Serviços (Controller/)

### 2. Service Layer
- Camada intermediária entre View e Model
- Encapsula lógica de negócio
- Gerencia transações

### 3. Repository Pattern
- Serviços atuam como repositórios
- Abstração do acesso a dados
- Interface `Gerenciavel<T>`

### 4. Template Method
- `UniversalObject` define estrutura base
- Subclasses implementam métodos específicos
- Reutilização de código comum

### 5. Strategy Pattern
- Diferentes tipos de pagamento
- Diferentes tipos de relatório
- Diferentes tipos de cliente

### 6. Factory Pattern
- `GeradorID` para criação de IDs únicos
- Criação de usuários por tipo
- Criação de relatórios

### 7. Singleton
- `DatabaseConnection` (conexão única)
- `AuthenticationManager` (sessão única)
- `AuditService` (serviço único)

### 8. Observer Pattern
- Notificações de estoque baixo
- Atualizações de interface
- Eventos de sistema

### 9. Facade Pattern
- Serviços simplificam acesso ao Model
- Interface unificada para operações complexas

### 10. DAO (Data Access Object)
- `DatabaseConnection` abstrai persistência
- Separação entre lógica e dados

---

## Tratamento de Erros

### Estratégia de Exceções

**Hierarquia de Exceções Customizadas:**
\`\`\`java
SystemException (base)
  ├── AuthenticationException
  ├── ValidationException
  ├── BusinessRuleException
  ├── DatabaseException
  └── NotFoundException
\`\`\`

**Tratamento em Camadas:**

**View (Telas):**
- Captura exceções de serviços
- Exibe mensagens amigáveis ao usuário
- Registra erros em log
- Não expõe detalhes técnicos

**Service (Serviços):**
- Valida regras de negócio
- Lança exceções específicas
- Registra em AuditLog
- Faz rollback de transações

**Model (Entidades):**
- Validações básicas de atributos
- Lança exceções de validação
- Mantém consistência de dados

**Exemplo de Tratamento:**
\`\`\`java
// Na View
try {
    vendasService.finalizarOrdem(ordem);
    JOptionPane.showMessageDialog(this, "Ordem finalizada com sucesso!");
} catch (ValidationException e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
} catch (BusinessRuleException e) {
    JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Negócio", JOptionPane.ERROR_MESSAGE);
} catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Erro inesperado. Contate o administrador.", "Erro", JOptionPane.ERROR_MESSAGE);
    auditService.registrarErro(e);
}
\`\`\`

### Validações

**FieldValidator**
- Valida campos de formulário
- Regras de negócio básicas
- Formatos de dados

**Validações Implementadas:**
- Email válido
- Telefone válido
- CPF/NUIT válido
- Campos obrigatórios
- Tamanho mínimo/máximo
- Valores numéricos positivos
- Datas válidas

---

## Configuração e Deployment

### Requisitos do Sistema

**Hardware Mínimo:**
- Processador: 1.5 GHz
- RAM: 2 GB
- Disco: 500 MB livres

**Software:**
- Java Runtime Environment (JRE) 8+
- Sistema Operacional: Windows 7+, Linux, macOS

### Configuração

**Arquivo de Configuração (config.properties):**
```properties
# Database
database.path=./data
database.backup.enabled=true
database.backup.interval=24

# Security
security.max.login.attempts=3
security.session.timeout=30

# System
system.language=pt_BR
system.currency=MZN
system.date.format=dd/MM/yyyy
