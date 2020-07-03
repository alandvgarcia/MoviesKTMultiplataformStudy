//
//  ContentView.swift
//  KotlinIOS
//
//  Created by Alan Vargas on 05/03/20.
//  Copyright © 2020 Alan Vargas. All rights reserved.
//

import SwiftUI
import SharedCode

struct ContentView: View {
    
    @ObservedObject var viewModel = ContentViewModel()
    
    var body: some View {
        NavigationView {
            List(viewModel.resultsPaging, id: \.title){ movie in
                Text(movie.title)
            }
        .navigationBarTitle("KotlinMultiplataform")
            .navigationBarItems(leading: HStack{
                Button("Request"){
                    self.viewModel.request()
                }
                Button("Request DB"){
                    self.viewModel.requestDb()
                }
            })
        }
        
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
