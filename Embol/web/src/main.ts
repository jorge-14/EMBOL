import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
// import { provideKeycloak } from 'keycloak-angular';

async function bootstrap() {
  try {
    /*
    const response = await fetch('/assets/keycloak-config.json');
    const keycloakConfig = await response.json();

    const extendedConfig = {
      ...appConfig,
      providers: [
        ...(appConfig.providers || []),
        provideKeycloak({
          config: keycloakConfig,
          initOptions: {
            onLoad: 'check-sso',
            silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
            pkceMethod: 'S256'
          }
        })
      ]
    };

    await bootstrapApplication(App, extendedConfig);
    */
    await bootstrapApplication(App, appConfig);
  } catch (err) {
    console.error('Failed to bootstrap application', err);
  }
}

bootstrap();
