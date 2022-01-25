declare module 'react-native-shared-group-preferences' {
  export interface SharedGroupPreferencesStatic {
    isAppInstalledAndroid: (packageName: string) => Promise<void>;
    getItem: <T = any>(key: string, appGroup: string) => Promise<T>;
    setItem: <T = any>(key: string, value: T, appGroup: string) => Promise<void>;
  }

  const SharedGroupPreferences: SharedGroupPreferencesStatic;

  export default SharedGroupPreferences;
}