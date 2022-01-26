import { NativeModules, Platform } from 'react-native'
const { RNReactNativeSharedGroupPreferences } = NativeModules

export default class SharedGroupPreferences {

  static async isAppInstalledAndroid(packageName) {
    return new Promise((resolve, reject)=>{
      RNReactNativeSharedGroupPreferences.isAppInstalledAndroid(packageName, installed=>{
        if (installed) {
          resolve()
        } else {
          reject()
        }
      })
    })
  }

  static async getItem(key, appGroup) {
    return new Promise((resolve, reject)=>{
      if ((Platform.OS != 'ios') && (Platform.OS != 'android')) {
        reject(Platform.OS)
      }

     
      RNReactNativeSharedGroupPreferences.getItem(key, appGroup, (errorCode, item)=>{
        if (errorCode != null) {
          reject(errorCode)
        } else {
          try {
            const result = JSON.parse(item);
            resolve(result)
          }catch(error){
            reject(error)
          }
        }
      })
    })
  }

  static async setItem(key, value, appGroup) {
    return new Promise((resolve, reject)=>{
      if ((Platform.OS != 'ios') && (Platform.OS != 'android')) {
        reject(Platform.OS)
      }

      
      RNReactNativeSharedGroupPreferences.setItem(key, JSON.stringify(value), appGroup, errorCode=>{
        if (errorCode != null) {
          reject(errorCode)
        } else {
          resolve()
        }
      })
    })
  }
/*
  static async saveFile(filenameAndKey, urlToFile, appGroup, inputOptions) {
    return new Promise((resolve, reject)=>{
      if ((Platform.OS != 'ios') && (Platform.OS != 'android')) {
        reject(Platform.OS)
      }

      const options = inputOptions || {}
      RNReactNativeSharedGroupPreferences.saveFile(filenameAndKey, urlToFile, appGroup, options, errorCode=>{
        if (errorCode != null) {
          reject(errorCode)
        } else {
          resolve()
        }
      })
    })
  }

  static async getUrlToFile(filenameAndKey, appGroup, inputOptions) {
    return new Promise((resolve, reject)=>{
      if ((Platform.OS != 'ios') && (Platform.OS != 'android')) {
        reject(Platform.OS)
      }

      const options = inputOptions || {}
      RNReactNativeSharedGroupPreferences.getUrlToFile(filenameAndKey, appGroup, options, (errorCode, item)=>{
        if (errorCode != null) {
          reject(errorCode)
        } else {
          resolve(JSON.parse(item))
        }
      })
    })
  }
  */
}
