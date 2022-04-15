export class AlreadyLoadedCoreModuleError extends Error {
  constructor() {
    super("CoreModule is already loaded. Import it in AppModule only")
  }
}
