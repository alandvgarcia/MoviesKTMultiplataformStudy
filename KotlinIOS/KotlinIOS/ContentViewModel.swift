//
//  ContentViewModel.swift
//  KotlinIOS
//
//  Created by Alan Vargas on 09/03/20.
//  Copyright © 2020 Alan Vargas. All rights reserved.
//

import Foundation
import SharedCode	

class ContentViewModel : ObservableObject{
    
    @Published var about : String = ""
    @Published var resultsPaging : [MovieEntity] = []
    
    private let movieApi = MovieApi(apiKey: "8aa61303fe43973122e7b287a5c13c42", language: "en-US")
    private var driverFactory = DriverFactory.init()
    private let movieDataBase: MovieDatabase

    
    
    private var repository: MovieRepository
    
    init() {
        movieDataBase = DriverFactoryKt.createDatabase(driverFactory: driverFactory)
        repository = MovieRepository(movieDatabase: movieDataBase, movieApi: movieApi)
        
      
    }
    
    
    func requestDb(){
        repository.getSavedMovies(){ movies in
                       movies.forEach{ movie in
                           print(movie)
                       }
            self.resultsPaging = movies
                   }
    }
    
    func request(){
        repository.getMovies(page: 1){ movies in
            movies.forEach{ movie in
                print(movie.title)
            }
             self.resultsPaging = movies
        }
    }
}
