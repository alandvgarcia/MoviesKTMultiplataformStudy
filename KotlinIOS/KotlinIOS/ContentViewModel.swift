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
    @Published var resultsPaging : [Movie] = []
    
    private let movieApi = MovieApi(apiKey: "8aa61303fe43973122e7b287a5c13c42", language: "en-US")
    
    init() {
        movieApi.getPopularMovies(page: 1) { resultPagingApi in
            self.resultsPaging = resultPagingApi
        }
    }
    
}
