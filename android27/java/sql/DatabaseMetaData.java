package java.sql;

public interface DatabaseMetaData extends Wrapper
{
    public static final int procedureResultUnknown = 0;
    public static final int procedureNoResult = 1;
    public static final int procedureReturnsResult = 2;
    public static final int procedureColumnUnknown = 0;
    public static final int procedureColumnIn = 1;
    public static final int procedureColumnInOut = 2;
    public static final int procedureColumnOut = 4;
    public static final int procedureColumnReturn = 5;
    public static final int procedureColumnResult = 3;
    public static final int procedureNoNulls = 0;
    public static final int procedureNullable = 1;
    public static final int procedureNullableUnknown = 2;
    public static final int columnNoNulls = 0;
    public static final int columnNullable = 1;
    public static final int columnNullableUnknown = 2;
    public static final int bestRowTemporary = 0;
    public static final int bestRowTransaction = 1;
    public static final int bestRowSession = 2;
    public static final int bestRowUnknown = 0;
    public static final int bestRowNotPseudo = 1;
    public static final int bestRowPseudo = 2;
    public static final int versionColumnUnknown = 0;
    public static final int versionColumnNotPseudo = 1;
    public static final int versionColumnPseudo = 2;
    public static final int importedKeyCascade = 0;
    public static final int importedKeyRestrict = 1;
    public static final int importedKeySetNull = 2;
    public static final int importedKeyNoAction = 3;
    public static final int importedKeySetDefault = 4;
    public static final int importedKeyInitiallyDeferred = 5;
    public static final int importedKeyInitiallyImmediate = 6;
    public static final int importedKeyNotDeferrable = 7;
    public static final int typeNoNulls = 0;
    public static final int typeNullable = 1;
    public static final int typeNullableUnknown = 2;
    public static final int typePredNone = 0;
    public static final int typePredChar = 1;
    public static final int typePredBasic = 2;
    public static final int typeSearchable = 3;
    public static final short tableIndexStatistic = 0;
    public static final short tableIndexClustered = 1;
    public static final short tableIndexHashed = 2;
    public static final short tableIndexOther = 3;
    public static final short attributeNoNulls = 0;
    public static final short attributeNullable = 1;
    public static final short attributeNullableUnknown = 2;
    public static final int sqlStateXOpen = 1;
    public static final int sqlStateSQL = 2;
    public static final int sqlStateSQL99 = 2;
    public static final int functionColumnUnknown = 0;
    public static final int functionColumnIn = 1;
    public static final int functionColumnInOut = 2;
    public static final int functionColumnOut = 3;
    public static final int functionReturn = 4;
    public static final int functionColumnResult = 5;
    public static final int functionNoNulls = 0;
    public static final int functionNullable = 1;
    public static final int functionNullableUnknown = 2;
    public static final int functionResultUnknown = 0;
    public static final int functionNoTable = 1;
    public static final int functionReturnsTable = 2;
    
    boolean allProceduresAreCallable() throws SQLException;
    
    boolean allTablesAreSelectable() throws SQLException;
    
    String getURL() throws SQLException;
    
    String getUserName() throws SQLException;
    
    boolean isReadOnly() throws SQLException;
    
    boolean nullsAreSortedHigh() throws SQLException;
    
    boolean nullsAreSortedLow() throws SQLException;
    
    boolean nullsAreSortedAtStart() throws SQLException;
    
    boolean nullsAreSortedAtEnd() throws SQLException;
    
    String getDatabaseProductName() throws SQLException;
    
    String getDatabaseProductVersion() throws SQLException;
    
    String getDriverName() throws SQLException;
    
    String getDriverVersion() throws SQLException;
    
    int getDriverMajorVersion();
    
    int getDriverMinorVersion();
    
    boolean usesLocalFiles() throws SQLException;
    
    boolean usesLocalFilePerTable() throws SQLException;
    
    boolean supportsMixedCaseIdentifiers() throws SQLException;
    
    boolean storesUpperCaseIdentifiers() throws SQLException;
    
    boolean storesLowerCaseIdentifiers() throws SQLException;
    
    boolean storesMixedCaseIdentifiers() throws SQLException;
    
    boolean supportsMixedCaseQuotedIdentifiers() throws SQLException;
    
    boolean storesUpperCaseQuotedIdentifiers() throws SQLException;
    
    boolean storesLowerCaseQuotedIdentifiers() throws SQLException;
    
    boolean storesMixedCaseQuotedIdentifiers() throws SQLException;
    
    String getIdentifierQuoteString() throws SQLException;
    
    String getSQLKeywords() throws SQLException;
    
    String getNumericFunctions() throws SQLException;
    
    String getStringFunctions() throws SQLException;
    
    String getSystemFunctions() throws SQLException;
    
    String getTimeDateFunctions() throws SQLException;
    
    String getSearchStringEscape() throws SQLException;
    
    String getExtraNameCharacters() throws SQLException;
    
    boolean supportsAlterTableWithAddColumn() throws SQLException;
    
    boolean supportsAlterTableWithDropColumn() throws SQLException;
    
    boolean supportsColumnAliasing() throws SQLException;
    
    boolean nullPlusNonNullIsNull() throws SQLException;
    
    boolean supportsConvert() throws SQLException;
    
    boolean supportsConvert(final int p0, final int p1) throws SQLException;
    
    boolean supportsTableCorrelationNames() throws SQLException;
    
    boolean supportsDifferentTableCorrelationNames() throws SQLException;
    
    boolean supportsExpressionsInOrderBy() throws SQLException;
    
    boolean supportsOrderByUnrelated() throws SQLException;
    
    boolean supportsGroupBy() throws SQLException;
    
    boolean supportsGroupByUnrelated() throws SQLException;
    
    boolean supportsGroupByBeyondSelect() throws SQLException;
    
    boolean supportsLikeEscapeClause() throws SQLException;
    
    boolean supportsMultipleResultSets() throws SQLException;
    
    boolean supportsMultipleTransactions() throws SQLException;
    
    boolean supportsNonNullableColumns() throws SQLException;
    
    boolean supportsMinimumSQLGrammar() throws SQLException;
    
    boolean supportsCoreSQLGrammar() throws SQLException;
    
    boolean supportsExtendedSQLGrammar() throws SQLException;
    
    boolean supportsANSI92EntryLevelSQL() throws SQLException;
    
    boolean supportsANSI92IntermediateSQL() throws SQLException;
    
    boolean supportsANSI92FullSQL() throws SQLException;
    
    boolean supportsIntegrityEnhancementFacility() throws SQLException;
    
    boolean supportsOuterJoins() throws SQLException;
    
    boolean supportsFullOuterJoins() throws SQLException;
    
    boolean supportsLimitedOuterJoins() throws SQLException;
    
    String getSchemaTerm() throws SQLException;
    
    String getProcedureTerm() throws SQLException;
    
    String getCatalogTerm() throws SQLException;
    
    boolean isCatalogAtStart() throws SQLException;
    
    String getCatalogSeparator() throws SQLException;
    
    boolean supportsSchemasInDataManipulation() throws SQLException;
    
    boolean supportsSchemasInProcedureCalls() throws SQLException;
    
    boolean supportsSchemasInTableDefinitions() throws SQLException;
    
    boolean supportsSchemasInIndexDefinitions() throws SQLException;
    
    boolean supportsSchemasInPrivilegeDefinitions() throws SQLException;
    
    boolean supportsCatalogsInDataManipulation() throws SQLException;
    
    boolean supportsCatalogsInProcedureCalls() throws SQLException;
    
    boolean supportsCatalogsInTableDefinitions() throws SQLException;
    
    boolean supportsCatalogsInIndexDefinitions() throws SQLException;
    
    boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException;
    
    boolean supportsPositionedDelete() throws SQLException;
    
    boolean supportsPositionedUpdate() throws SQLException;
    
    boolean supportsSelectForUpdate() throws SQLException;
    
    boolean supportsStoredProcedures() throws SQLException;
    
    boolean supportsSubqueriesInComparisons() throws SQLException;
    
    boolean supportsSubqueriesInExists() throws SQLException;
    
    boolean supportsSubqueriesInIns() throws SQLException;
    
    boolean supportsSubqueriesInQuantifieds() throws SQLException;
    
    boolean supportsCorrelatedSubqueries() throws SQLException;
    
    boolean supportsUnion() throws SQLException;
    
    boolean supportsUnionAll() throws SQLException;
    
    boolean supportsOpenCursorsAcrossCommit() throws SQLException;
    
    boolean supportsOpenCursorsAcrossRollback() throws SQLException;
    
    boolean supportsOpenStatementsAcrossCommit() throws SQLException;
    
    boolean supportsOpenStatementsAcrossRollback() throws SQLException;
    
    int getMaxBinaryLiteralLength() throws SQLException;
    
    int getMaxCharLiteralLength() throws SQLException;
    
    int getMaxColumnNameLength() throws SQLException;
    
    int getMaxColumnsInGroupBy() throws SQLException;
    
    int getMaxColumnsInIndex() throws SQLException;
    
    int getMaxColumnsInOrderBy() throws SQLException;
    
    int getMaxColumnsInSelect() throws SQLException;
    
    int getMaxColumnsInTable() throws SQLException;
    
    int getMaxConnections() throws SQLException;
    
    int getMaxCursorNameLength() throws SQLException;
    
    int getMaxIndexLength() throws SQLException;
    
    int getMaxSchemaNameLength() throws SQLException;
    
    int getMaxProcedureNameLength() throws SQLException;
    
    int getMaxCatalogNameLength() throws SQLException;
    
    int getMaxRowSize() throws SQLException;
    
    boolean doesMaxRowSizeIncludeBlobs() throws SQLException;
    
    int getMaxStatementLength() throws SQLException;
    
    int getMaxStatements() throws SQLException;
    
    int getMaxTableNameLength() throws SQLException;
    
    int getMaxTablesInSelect() throws SQLException;
    
    int getMaxUserNameLength() throws SQLException;
    
    int getDefaultTransactionIsolation() throws SQLException;
    
    boolean supportsTransactions() throws SQLException;
    
    boolean supportsTransactionIsolationLevel(final int p0) throws SQLException;
    
    boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException;
    
    boolean supportsDataManipulationTransactionsOnly() throws SQLException;
    
    boolean dataDefinitionCausesTransactionCommit() throws SQLException;
    
    boolean dataDefinitionIgnoredInTransactions() throws SQLException;
    
    ResultSet getProcedures(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getProcedureColumns(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    ResultSet getTables(final String p0, final String p1, final String p2, final String[] p3) throws SQLException;
    
    ResultSet getSchemas() throws SQLException;
    
    ResultSet getCatalogs() throws SQLException;
    
    ResultSet getTableTypes() throws SQLException;
    
    ResultSet getColumns(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    ResultSet getColumnPrivileges(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    ResultSet getTablePrivileges(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getBestRowIdentifier(final String p0, final String p1, final String p2, final int p3, final boolean p4) throws SQLException;
    
    ResultSet getVersionColumns(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getPrimaryKeys(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getImportedKeys(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getExportedKeys(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getCrossReference(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5) throws SQLException;
    
    ResultSet getTypeInfo() throws SQLException;
    
    ResultSet getIndexInfo(final String p0, final String p1, final String p2, final boolean p3, final boolean p4) throws SQLException;
    
    boolean supportsResultSetType(final int p0) throws SQLException;
    
    boolean supportsResultSetConcurrency(final int p0, final int p1) throws SQLException;
    
    boolean ownUpdatesAreVisible(final int p0) throws SQLException;
    
    boolean ownDeletesAreVisible(final int p0) throws SQLException;
    
    boolean ownInsertsAreVisible(final int p0) throws SQLException;
    
    boolean othersUpdatesAreVisible(final int p0) throws SQLException;
    
    boolean othersDeletesAreVisible(final int p0) throws SQLException;
    
    boolean othersInsertsAreVisible(final int p0) throws SQLException;
    
    boolean updatesAreDetected(final int p0) throws SQLException;
    
    boolean deletesAreDetected(final int p0) throws SQLException;
    
    boolean insertsAreDetected(final int p0) throws SQLException;
    
    boolean supportsBatchUpdates() throws SQLException;
    
    ResultSet getUDTs(final String p0, final String p1, final String p2, final int[] p3) throws SQLException;
    
    Connection getConnection() throws SQLException;
    
    boolean supportsSavepoints() throws SQLException;
    
    boolean supportsNamedParameters() throws SQLException;
    
    boolean supportsMultipleOpenResults() throws SQLException;
    
    boolean supportsGetGeneratedKeys() throws SQLException;
    
    ResultSet getSuperTypes(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getSuperTables(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getAttributes(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    boolean supportsResultSetHoldability(final int p0) throws SQLException;
    
    int getResultSetHoldability() throws SQLException;
    
    int getDatabaseMajorVersion() throws SQLException;
    
    int getDatabaseMinorVersion() throws SQLException;
    
    int getJDBCMajorVersion() throws SQLException;
    
    int getJDBCMinorVersion() throws SQLException;
    
    int getSQLStateType() throws SQLException;
    
    boolean locatorsUpdateCopy() throws SQLException;
    
    boolean supportsStatementPooling() throws SQLException;
    
    RowIdLifetime getRowIdLifetime() throws SQLException;
    
    ResultSet getSchemas(final String p0, final String p1) throws SQLException;
    
    boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException;
    
    boolean autoCommitFailureClosesAllResultSets() throws SQLException;
    
    ResultSet getClientInfoProperties() throws SQLException;
    
    ResultSet getFunctions(final String p0, final String p1, final String p2) throws SQLException;
    
    ResultSet getFunctionColumns(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    ResultSet getPseudoColumns(final String p0, final String p1, final String p2, final String p3) throws SQLException;
    
    boolean generatedKeyAlwaysReturned() throws SQLException;
    
    default long getMaxLogicalLobSize() throws SQLException {
        return 0L;
    }
    
    default boolean supportsRefCursors() throws SQLException {
        return false;
    }
}
